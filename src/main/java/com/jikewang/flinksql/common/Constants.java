package com.jikewang.flinksql.common;

/**
 * @ProjectName: flinkdemo2
 * @Package: com.jikewang.flinksql.common
 * @ClassName: Constants
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/3/12 17:08
 * @Version: 1.0
 */
public class Constants {
    public static final String SYS_NAME = "FLINK_SQL";

    public static final String LOG_SYS_NAME = "flinksql";

    public static final String REDIS_PREFIX = "THEMIS:";

    public static final String REDIS_RULEPOOL_KEY = "RPK";

    public static final String REDIS_COMPOUND_KEY = "RCPK:";

    public static final String REDIS_RATE_KEY = "RRA:";

    public static final int REDIS_COMPOUND_CACHE_EXPIRE = 10;

    public static final String HBASE_NAMESPACE = "Themis:";

    public static final String HBASE_DEFAULT_FAMILY = "cf";

    public static final int HBASE_SCAN_BATCH = 10000;

    public static final int HBASE_SCAN_CACHE = 1000;

    public static final int HBASE_PUT_CACHE_SIZE = 100;

    public static final int HBASE_PUT_CACHE_MAX_WINDOW = 500;

    public static final String HBASE_TABLE_ALERT_EVENT = "alert_event";

    public static final String HBASE_TABLE_EVENT_TEMP_1 = "event_temp_1d";

    public static final String HBASE_TABLE_EVENT_TEMP_2 = "event_temp_7d";

    public static final String HBASE_TABLE_EVENT_TEMP_3 = "event_temp_1m";

    public static final String HBASE_TABLE_ALERT_TRACE = "alert_trace";

    public static final String HBASE_TABLE_TRACE_TEMP = "trace_temp";

    public static final String EVENT_DEFAULT_SUCCESS_TAG = "Y";

    public static final String EVENT_DEFAULT_FAILURE_TAG = "N";

    public static final int EVENT_PROJECT_VALID_LENGTH = 32;

    public static final int EVENT_METHOD_VALID_LENGTH = 256;

    public static final int EVENT_CODE_VALID_LENGTH = 32;

    public static final int TRACE_MAX_EVENT_SIZE = 100;

    public static final String DASH_MEASUREMENT = "alert_info";

    public static final String METHOD_NULL_KEY = "MNULK";

    public static final String TRIGGER_TYPE = "RULE";

    public static final String CUSTOM_FIELD_PREFIX = "CUS";

    public static final String SQL_FIELD_PREFIX = "SQL";

    public static final String INFLUX_FIELD_PREFIX = "INF";

    public static final String KEYBY_SEPARATE = ",";

    public static final String GROUPKEY_SEPARATE = "&&";

    public static final String AGGR_RESULT_PREFIX = "AGGR";

    public static final String AGGR_NUMBER_PREFIX = "AGGRN";

    public static final int AGGR_EVENT_ID_MAX = 1000;

    public static final String RATE_RESULT_KEY = "RATE";

    public static final String RATE_CURRENT_KEY = "RATEC";

    public static final String RATE_OLD_RESULT_KEY = "RATEO";

    public static final String SELECT_ENV_PARM = "env";

    public static final String SELECT_ENV_SEPARATE = ";";

    public static final String SQL_PARAM_SEPARATE = "&&";

    public static final String SQL_STATEMENT_PLACEHOLDER = "%";

    public static final String SQL_TYPE_SELECT = "select";

    public static final String COMPOUND_SUBRULE_PREFIX = "sub_";

    public static final String COMPOUND_AND_TAG = "&&AND&&";

    public static final String COMPOUND_OR_TAG = "&&OR&&";

    public static final String WINDOW_INFO_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final int OPTIMIZE_RATEO_MIN_NUMBER = 10;

    public static final int DESCRIBE_FIELD_MAX_LENGTH = 30;

    public static final String BUSSINESS_FIELD_SEPORATOR = "&&";

    public static final String BUSSINESS_FIELD_DESC_SEPORATOR = "\\$\\$";
}
