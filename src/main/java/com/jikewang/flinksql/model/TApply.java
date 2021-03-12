package com.jikewang.flinksql.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ProjectName: flinkdemo2
 * @Package: com.jikewang.flinksql.function
 * @ClassName: TApply
 * @Author: wangyaqiang
 * @Description:
 * @Date: 2021/3/12 17:23
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class TApply implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String create_time;
    private String last_update_time;
    private String state;
    private String state_manual;
    private String name;
    private String id_no;
    private Double loan_amount;
    private Double approval_money_amount;
    private Double approval_money_amount_manual;
    private Integer loan_phases;
    private String applied_product_type;
    private String group_id;
}

