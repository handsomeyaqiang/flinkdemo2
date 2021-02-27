package com.jikewang.flink;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.apache.flink.walkthrough.common.sink.AlertSink;
import org.apache.flink.walkthrough.common.source.TransactionSource;

/**
 * @ProjectName: flinkdemo2
 * @Package: com.jikewang.flink
 * @ClassName: FraudDetectorJob
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/2/26 16:55
 * @Version: 1.0
 */
public class FraudDetectorJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Transaction> transactions = env.addSource(new TransactionSource()).name("transactions");
        DataStream<Alert> alerts = transactions.keyBy(Transaction::getAccountId)
                .process(new FraudDetector())
                .name("fraud-detector");
        alerts.addSink(new AlertSink());
        env.execute("Fraud Detector");
    }
}
