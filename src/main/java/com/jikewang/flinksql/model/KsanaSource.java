package com.jikewang.flinksql.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: flinkdemo2
 * @Package: com.jikewang.flinksql.model
 * @ClassName: KsanaSource
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/3/12 17:37
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class KsanaSource {
    private String ksana_system;
    private String ksana_type;
    private String ksana_source;
    private String ksana_oper;
    private long ksana_execute_time;
    private long ksana_kafka_time;
    private int ksana_delay;
    private List<String> ksana_columns;
    private Map<String, String> ksana_kvs;
    private Map<String, String> ksana_old_kvs;
}
