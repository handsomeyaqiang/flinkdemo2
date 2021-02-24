package com.jikewang.flink;

import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: streaming
 * @Package: com.jikewang.streaming.flink
 * @ClassName: FoldDemo
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/1/29 14:35
 * @Version: 1.0
 */
public class FoldDemo {
    private static final Logger logger = LoggerFactory.getLogger(FoldDemo.class);
    private static final String[] TYPE = {"苹果", "梨", "西瓜", "葡萄", "火龙果"};
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Tuple2<String, Integer>> orderSource = env.addSource(new SourceFunction<Tuple2<String, Integer>>() {
            private volatile boolean isRunning = true;
            private final Random random = new Random();

            @Override
            public void run(SourceContext<Tuple2<String, Integer>> sourceContext) throws Exception {
                while (isRunning) {
                    TimeUnit.SECONDS.sleep(1);
                    String type = TYPE[random.nextInt(TYPE.length)];
                    System.out.println("-------------------");
                    System.out.println(type);
                    sourceContext.collect(Tuple2.of(type, 1));
                }
            }

            @Override
            public void cancel() {
                isRunning = false;
            }
        }, "order-info");
        orderSource.keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                return "";
            }
        }).fold(new HashMap<>(), new FoldFunction<Tuple2<String, Integer>, HashMap<String, Integer>>() {
            @Override
            public HashMap<String, Integer> fold(HashMap<String, Integer> accumulator, Tuple2<String, Integer> value) throws Exception {
                accumulator.put(value.f0, accumulator.getOrDefault(value.f0, 0) + value.f1);
                return accumulator;
            }
        }).print();

        env.execute("Flink Streaming FoldDemo execute");
    }
}
