package com.jikewang.flink;

/**
 * @ProjectName: flinkdemo2
 * @Package: com.jikewang.flink
 * @ClassName: SendReport
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/2/26 17:57
 * @Version: 1.0
 */
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.Tumble;
import org.apache.flink.table.expressions.TimeIntervalUnit;

import static org.apache.flink.table.api.Expressions.*;

public class SpendReport {
    public static Table report(Table transactions){
        throw new UnimplementedException();
    }
    public static Table report2(Table transactions) {
        return transactions.select(
                $("account_id"),
                $("transaction_time").floor(TimeIntervalUnit.HOUR).as("log_ts"),
                $("amount"))
                .groupBy($("account_id"), $("log_ts"))
                .select($("account_id"),
                        $("log_ts"),
                        $("amount").sum().as("amount"));
    }

    public static Table report3(Table transactions){
        return transactions.select($("account_id"),
                call(MyFloor.class, $("transaction_time")).as("log_ts"),
                $("amount"))
                .groupBy($("account_id"),
                        $("log_ts"))
                .select($("account_id"),
                        $("log_ts"),
                        $("amount").sum().as("amount"));

    }

    public static Table report4(Table transactions){
        return transactions.window(Tumble.over(lit(1).hour()).on($("transaction_time")).as("log_ts"))
                .groupBy($("account_id"),$("log_ts"))
                .select($("account_id"),
                        $("log_ts"),
                        $("amount").sum().as("amount"));
    }

    public static void main(String[] args) throws Exception {
        // on stream mode
        EnvironmentSettings settings = EnvironmentSettings.newInstance().build();
        // on batch mode
//        EnvironmentSettings settings = EnvironmentSettings.newInstance().inBatchMode().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);

        tEnv.executeSql("CREATE TABLE transactions (\n" +
                "    account_id  BIGINT,\n" +
                "    amount      BIGINT,\n" +
                "    transaction_time TIMESTAMP(3),\n" +
                "    WATERMARK FOR transaction_time AS transaction_time - INTERVAL '5' SECOND\n" +
                ") WITH (\n" +
                "    'connector' = 'kafka',\n" +
                "    'topic'     = 'transactions',\n" +
                "    'properties.bootstrap.servers' = 'kafka:9092',\n" +
                "    'format'    = 'csv'\n" +
                ")");

        tEnv.executeSql("CREATE TABLE spend_report (\n" +
                "    account_id BIGINT,\n" +
                "    log_ts     TIMESTAMP(3),\n" +
                "    amount     BIGINT\n," +
                "    PRIMARY KEY (account_id, log_ts) NOT ENFORCED" +
                ") WITH (\n" +
                "  'connector'  = 'jdbc',\n" +
                "  'url'        = 'jdbc:mysql://mysql:3306/sql-demo',\n" +
                "  'table-name' = 'spend_report',\n" +
                "  'driver'     = 'com.mysql.jdbc.Driver',\n" +
                "  'username'   = 'root',\n" +
                "  'password'   = 'root'\n" +
                ")");

        Table transactions = tEnv.from("transactions");
        report(transactions).executeInsert("spend_report");
    }
}
