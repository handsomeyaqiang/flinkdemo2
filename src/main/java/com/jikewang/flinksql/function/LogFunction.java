package com.jikewang.flinksql.function;

import com.jikewang.flinksql.utils.CustomLogger;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;

/**
 * @ProjectName: flinkdemo2
 * @Package: com.jikewang.flinksql.function
 * @ClassName: LogFunction
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/3/12 17:51
 * @Version: 1.0
 */
public class LogFunction extends ProcessFunction<Tuple2<Boolean, Row>, String> {
    private CustomLogger logger;

    public void open(Configuration parameters) throws Exception {
        this.logger = new CustomLogger(LogFunction.class);
    }

    public void processElement(Tuple2<Boolean, Row> value, Context ctx, Collector<String> out) throws Exception {
        this.logger.info("Log", value.toString());
    }
}