package com.jikewang.flinksql;

import com.jikewang.flinksql.common.Variables;
import com.jikewang.flinksql.function.LogFunction;
import com.jikewang.flinksql.function.TranFunction;
import com.jikewang.flinksql.model.TApply;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

import java.util.Arrays;
import java.util.Properties;

/**
 * @ProjectName: flinkdemo2
 * @Package: com.jikewang.flinksql
 * @ClassName: Main
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/3/12 17:05
 * @Version: 1.0
 */
public class Main {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(Variables.getIntVar("task.parallelism"));
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        FlinkKafkaConsumer<String> consumer = initKafkaSource();
        DataStreamSource<String> dataStreamSource = env.addSource(consumer);
        SingleOutputStreamOperator<TApply> singleOutputStreamOperator = dataStreamSource.process(new TranFunction());
        tEnv.createTemporaryView("t_apply", singleOutputStreamOperator);
        Table sqlQuery = tEnv.sqlQuery("select applied_product_type, avg(approval_money_amount) as avg_approval_amount from t_apply GROUP BY applied_product_type");
        tEnv.toRetractStream(sqlQuery, Row.class).process(new LogFunction());
        env.execute("flink_sql");

    }

    private static FlinkKafkaConsumer<String> initKafkaSource() {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", Variables.getVar("kafka.bootstrap.servers"));
        kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProps.put("auto.offset.reset", "latest");
        kafkaProps.put("enable.auto.commit", "false");
        kafkaProps.setProperty("group.id", Variables.getVar("kafka.group.id"));
        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(Arrays.asList(Variables.getVar("kafka.topic").split(",")), new SimpleStringSchema(), kafkaProps);
        consumer.setStartFromLatest();
        return consumer;
    }
}
