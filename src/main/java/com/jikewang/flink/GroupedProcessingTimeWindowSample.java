package com.jikewang.flink;

import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.util.HashMap;
import java.util.Random;

/**
 * @ProjectName: streaming
 * @Package: com.jikewang.streaming.flink
 * @ClassName: GroupedProcessingTimeWindowSample
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/1/29 14:55
 * @Version: 1.0
 */
public class GroupedProcessingTimeWindowSample {
    public static void Demo1() throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        DataStreamSource<Tuple2<String, Integer>> dataStream = env.addSource(new DataSource());
        KeyedStream<Tuple2<String, Integer>, Tuple> keyedStream = dataStream.keyBy(0);
        keyedStream.sum(1).keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                return "";
            }
        }).fold(new HashMap<String, Integer>(), new FoldFunction<Tuple2<String, Integer>, HashMap<String, Integer>>() {
            @Override
            public HashMap<String, Integer> fold(HashMap<String, Integer> accumulator, Tuple2<String, Integer> value) throws Exception {
                accumulator.put(value.f0, value.f1);
                return accumulator;
            }
        }).addSink(new SinkFunction<HashMap<String, Integer>>() {
            @Override
            public void invoke(HashMap<String, Integer> value, Context context) throws Exception {
                // 每个类型商品成交量
                System.out.println(value);
                // 商品总成交量
                System.out.println(value.values().stream().mapToInt(v -> v).sum());
            }
        });
        env.execute("GroupedProcessing");
    }
    public static void Demo2() throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        DataStreamSource<Tuple2<String, Integer>> dataStream = env.addSource(new DataSource());
        dataStream.keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                return "";
            }
        }).timeWindow(Time.seconds(10)).fold(new HashMap<String, Integer>(), new FoldFunction<Tuple2<String, Integer>, HashMap<String, Integer>>() {
            @Override
            public HashMap<String, Integer> fold(HashMap<String, Integer> accumulator, Tuple2<String, Integer> value) throws Exception {
                accumulator.put(value.f0, accumulator.getOrDefault(value.f0, 0)+ value.f1);
                return accumulator;
            }
        }).addSink(new SinkFunction<HashMap<String, Integer>>() {
            @Override
            public void invoke(HashMap<String, Integer> value, Context context) throws Exception {
                // 每个类型商品成交量
                System.out.println(value);
                // 商品总成交量
                System.out.println(value.values().stream().mapToInt(v -> v).sum());
            }
        });
        env.execute("GroupedProcessing");
    }
    public static void main(String[] args) throws Exception {
        Demo2();
    }

    private static class DataSource extends RichParallelSourceFunction<Tuple2<String, Integer>> {
        private volatile boolean isRunning = true;
        private Random random = new Random();
        @Override
        public void run(SourceContext sourceContext) throws Exception {
            while (isRunning){
                Thread.sleep(1000 * 2);
                String key = "类别" + (char)('A' + random.nextInt(3));
                int value = random.nextInt(10) + 1;
                System.out.println(String.format("Emits\t(%s, %d)", key, value));
                sourceContext.collect(new Tuple2<>(key, value));
            }
        }

        @Override
        public void cancel() {
            isRunning = false;
        }
    }
}
