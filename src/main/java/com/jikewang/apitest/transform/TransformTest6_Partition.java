package com.jikewang.apitest.transform;
import com.jikewang.apitest.beans.SensorReading;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @ClassName: TransformTest6_Partition
 * @Description:
 * @Author: wushengran on 2020/11/9 10:14
 * @Version: 1.0
 */
public class TransformTest6_Partition {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);

        // 从文件读取数据
        DataStream<String> inputStream = env.readTextFile("D:\\YouXinProjection\\JavaProjection\\flinkdemo2\\src\\main\\resources\\sensor.txt");

        // 转换成SensorReading类型
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
        });

        dataStream.print("input");

        // 1. shuffle
        DataStream<String> shuffleStream = inputStream.shuffle();

//        shuffleStream.print("shuffle");

        // 2. keyBy

//        dataStream.keyBy("id").print("keyBy");

        // 3. global
        dataStream.global().print("global");

        env.execute();
    }
}
