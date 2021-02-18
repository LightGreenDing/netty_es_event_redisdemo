package com.example.demo.elasticsearch;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
@Data
@ToString
public class UserInfo implements Serializable {
    /** 姓名 */
    private String name;
    /** 地址 */
    private String address;
    /** 岁数 */
    private Integer age;
    /** 工资 */
    private Float salary;
    /** 出生日期 */
    private String birthDate;
    /** 备注信息 */
    private String remark;
    /** 创建时间 */
    private Date createTime;
}
