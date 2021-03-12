package com.jikewang.flinksql.utils;

/**
 * @ProjectName: flinkdemo2
 * @Package: com.jikewang.flinksql.utils
 * @ClassName: ExceptionTool
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/3/12 17:11
 * @Version: 1.0
 */
public class ExceptionTool {
    public static String getExceptionDetail(Exception e) {
        StringBuffer msg = new StringBuffer("null");
        if (e != null) {
            msg = new StringBuffer("");
            String message = e.toString();
            int length = e.getStackTrace().length;
            if (length > 0) {
                msg.append(message + "\n");
                for (int i = 0; i < length; i++) {
                    msg.append("\t" + e.getStackTrace()[i] + "\n");
                }
            } else {
                msg.append(message);
            }
        }
        return msg.toString();
    }
}
