package com.jikewang.flinksql.common;

import com.jikewang.flinksql.utils.ExceptionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ProjectName: flinkdemo2
 * @Package: com.jikewang.flinksql.common
 * @ClassName: Variables
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/3/12 17:09
 * @Version: 1.0
 */
public class Variables {
    private static Logger logger = LoggerFactory.getLogger(Variables.class);
    private static Properties properties = new Properties();
    private static Map<String, String> variables = new HashMap();

    static  {
        try {
            properties.load(
                    Thread.currentThread()
                            .getContextClassLoader()
                            .getResourceAsStream("constants.properties"));
            variables = new HashMap(properties);
        } catch (Exception e) {
            logger.error(ExceptionTool.getExceptionDetail(e));
        }
    }

    public static String getVar(String key) {
        if (key.startsWith("${") && key.endsWith("}")) {
            key = key.substring(2, key.length() - 1);
        }
        if (variables != null && !variables.isEmpty() && variables.containsKey(key)) {
            return (String)variables.get(key);
        }
        return null;
    }

    public static int getIntVar(String key) {
        if (key.startsWith("${") && key.endsWith("}")) {
            key = key.substring(2, key.length() - 1);
        }
        if (variables != null && !variables.isEmpty() && variables.containsKey(key)) {
            try {
                return Integer.parseInt((String)variables.get(key));
            } catch (NumberFormatException e) {
                logger.error(ExceptionTool.getExceptionDetail(e));
                return 0;
            }
        }
        return 0;
    }

    public static long getLongVar(String key) {
        if (key.startsWith("${") && key.endsWith("}")) {
            key = key.substring(2, key.length() - 1);
        }
        if (variables != null && !variables.isEmpty() && variables.containsKey(key)) {
            try {
                return Long.parseLong((String)variables.get(key));
            } catch (NumberFormatException e) {
                logger.error(ExceptionTool.getExceptionDetail(e));
                return 0L;
            }
        }
        return 0L;
    }

    public static double getDoubleVar(String key) {
        if (key.startsWith("${") && key.endsWith("}")) {
            key = key.substring(2, key.length() - 1);
        }
        if (variables != null && !variables.isEmpty() && variables.containsKey(key)) {
            try {
                return Double.parseDouble((String)variables.get(key));
            } catch (NumberFormatException e) {
                logger.error(ExceptionTool.getExceptionDetail(e));
                return 0.0D;
            }
        }
        return 0.0D;
    }

    public static float getFloatVar(String key) {
        if (key.startsWith("${") && key.endsWith("}")) {
            key = key.substring(2, key.length() - 1);
        }
        if (variables != null && !variables.isEmpty() && variables.containsKey(key)) {
            try {
                return Float.parseFloat((String)variables.get(key));
            } catch (NumberFormatException e) {
                logger.error(ExceptionTool.getExceptionDetail(e));
                return 0.0F;
            }
        }
        return 0.0F;
    }

    public static boolean getBooleanVar(String key) {
        if (key.startsWith("${") && key.endsWith("}")) {
            key = key.substring(2, key.length() - 1);
        }
        if (variables != null && !variables.isEmpty() && variables.containsKey(key)) {
            try {
                return Boolean.parseBoolean((String)variables.get(key));
            } catch (NumberFormatException e) {
                logger.error(ExceptionTool.getExceptionDetail(e));
                return false;
            }
        }
        return false;
    }

    public static boolean isVar(String key) {
        if (key.startsWith("${") && key.endsWith("}")) {
            return true;
        }
        return false;
    }
}
