package com.jikewang.flink;

/**
 * @ProjectName: flinkdemo2
 * @Package: com.jikewang.flink
 * @ClassName: UnimplementedException
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/2/26 17:58
 * @Version: 1.0
 */
public class UnimplementedException extends RuntimeException {

    public UnimplementedException() {
        super("This method has not yet been implemented");
    }
}
