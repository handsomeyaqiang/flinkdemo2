package com.jikewang.flinksql.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @ProjectName: flinkdemo2
 * @Package: com.jikewang.flinksql.utils
 * @ClassName: CustomLogger
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/3/12 17:28
 * @Version: 1.0
 */
public class CustomLogger implements Serializable {
    private static final long serialVersionUID = 1L;
    private String SEPARATOR;
    private String NULL;
    private String DEFAULT;
    private Logger logger;

    public CustomLogger(Class<?> c) {
        this.SEPARATOR = "^|";
        this.NULL = "NULL";
        this.DEFAULT = "DEFAULT";
        this.logger = LoggerFactory.getLogger(c);
    }

    public void info(String method, String message) {
        this.logger.info(message(method, this.DEFAULT, this.DEFAULT, message, "Y"));
    }

    public void info(String method, String eventId, String message) {
        this.logger.info(message(method, eventId, this.DEFAULT, message, "Y"));
    }

    public void info(String method, String eventId, String ruleId, String message) {
        this.logger.info(message(method, eventId, ruleId, message, "Y"));
    }

    public void warn(String method, String message) {
        this.logger.warn(message(method, this.DEFAULT, this.DEFAULT, message, "Y"));
    }

    public void warn(String method, String eventId, String message) {
        this.logger.warn(message(method, eventId, this.DEFAULT, message, "Y"));
    }

    public void warn(String method, String eventId, String ruleId, String message) {
        this.logger.warn(message(method, eventId, ruleId, message, "Y"));
    }

    public void error(String method, String message) {
        this.logger.error(message(method, this.DEFAULT, this.DEFAULT, message, "N"));
    }

    public void error(String method, String eventId, String message) {
        this.logger.error(message(method, eventId, this.DEFAULT, message, "N"));
    }

    public void error(String method, String eventId, String ruleId, String message) {
        this.logger.error(message(method, eventId, ruleId, message, "N"));
    }

    private String message(String method, String eventId, String ruleId, String message, String status) {
        StringBuffer log = new StringBuffer();
        String logEventId = eventId;
        String logRuleId = ruleId;
        String logMessage = message;
        if (StringUtils.isEmpty(logEventId)) {
            logEventId = this.NULL;
        }
        if (StringUtils.isEmpty(logRuleId)) {
            logRuleId = this.NULL;
        }
        if (StringUtils.isEmpty(logMessage)) {
            logMessage = this.NULL;
        }
        log.append(method)
                .append(this.SEPARATOR)
                .append(status)
                .append(this.SEPARATOR)
                .append(logEventId)
                .append(this.SEPARATOR)
                .append(logRuleId)
                .append(this.SEPARATOR)
                .append(logMessage);
        return log.toString();
    }
}
