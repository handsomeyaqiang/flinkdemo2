package com.jikewang.flinksql.function;

import com.alibaba.fastjson.JSON;
import com.jikewang.flinksql.model.KsanaSource;
import com.jikewang.flinksql.model.TApply;
import com.jikewang.flinksql.utils.CustomLogger;
import com.jikewang.flinksql.utils.ExceptionTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

/**
 * @ProjectName: flinkdemo2
 * @Package: com.jikewang.flinksql
 * @ClassName: TranFunction
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/3/12 17:22
 * @Version: 1.0
 */
public class TranFunction extends ProcessFunction<String, TApply> {
    private CustomLogger logger;
    @Override
    public void open(Configuration parameters) throws Exception {
        this.logger = new CustomLogger(TranFunction.class);
    }

    @Override
    public void processElement(String value, Context ctx, Collector<TApply> out) throws Exception {
        try {
            KsanaSource ksanaSource = JSON.parseObject(value, KsanaSource.class);
            if (ksanaSource.getKsana_source().equalsIgnoreCase("t_apply")) {
                TApply tApply = JSON.parseObject(JSON.toJSONString(ksanaSource.getKsana_kvs()), TApply.class);
                out.collect(tApply);
                this.logger.info("Tran", JSON.toJSONString(tApply));
            }
        } catch (Exception e) {
            this.logger.error("Tran", ExceptionTool.getExceptionDetail(e));
        }

    }
}
