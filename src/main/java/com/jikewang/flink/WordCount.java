package com.jikewang.flink;

import com.jikewang.data.WordCountData;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.MultipleParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.apache.flink.util.Preconditions;

/**
 * @ProjectName: myfamily
 * @Package: com.jikewang.myfamily.flink
 * @ClassName: WordCount
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/1/28 10:57
 * @Version: 1.0
 */
public class WordCount {
    public static void main(String[] args) throws Exception {
        final MultipleParameterTool params = MultipleParameterTool.fromArgs(args);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setGlobalJobParameters(params);
        DataStream<String> text = null;
        if (params.has("input")){
            for (String input : params.getMultiParameterRequired("input")){
                if (text == null){
                    text = env.readTextFile(input);
                }else {
                    text = text.union(env.readTextFile(input));
                }
            }
            Preconditions.checkNotNull(text, "Input DataStream should not be null");
        }else{
            System.out.println("Executing WordCount example with default input data set.");
            System.out.println("Use --input to specify file input.");
            text = env.fromElements(WordCountData.WORDS);
        }
        DataStream<Tuple2<String, Integer>> counts = text.flatMap(new Tokenizer())
                .keyBy(0)
                .sum(1);
        if (params.has("output")){
            counts.writeAsText(params.get("output"));
        }else{
            System.out.println("Printing result to stdout. Use --output to specify output path.");
            counts.print();
        }
        env.execute("Streaming WordCount");
    }

    public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
            String[] tokens = value.toLowerCase().split("\\W+");
            for (String token : tokens){
                if (token.length() > 0){
                    out.collect(new Tuple2<>(token, 1));
                }
            }
        }
    }
}
