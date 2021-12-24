package com.olivine.feign.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author jphao
 * @Date 2021/11/26 22:24
 * @Description
 */
@Data
public class AddressDTO implements Serializable {
    private static final long serialVersionUID = 6177850989989154622L;

    private String uid;

    // 收货人
    private String name;

    // 手机号
    private String phone;

    // 所在地区
    private String region;

    // 详细地址
    private String detail;

    private Date ctime;

    private Date mtime;
}
