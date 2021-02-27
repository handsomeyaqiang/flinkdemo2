package com.jikewang.flink;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.catalog.DataTypeFactory;
import org.apache.flink.table.functions.FunctionKind;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.table.functions.UserDefinedFunction;
import org.apache.flink.table.types.inference.TypeInference;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @ProjectName: flinkdemo2
 * @Package: com.jikewang.flink
 * @ClassName: MyFloor
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/2/26 18:19
 * @Version: 1.0
 */
public class MyFloor extends ScalarFunction {
    public @DataTypeHint("TIMESTAMP(3)") LocalDateTime eval(
            @DataTypeHint("TIMESTAMP(3)") LocalDateTime timestamp) {

        return timestamp.truncatedTo(ChronoUnit.HOURS);
    }
}
