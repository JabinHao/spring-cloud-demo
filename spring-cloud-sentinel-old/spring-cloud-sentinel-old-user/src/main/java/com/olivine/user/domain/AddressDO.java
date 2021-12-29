package com.olivine.user.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author jphao
 * @Date 2021/11/26 21:17
 * @Description
 */
@Data
public class AddressDO {

    private Long id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 收货人
     */
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 所在地区
     */
    private String region;

    /**
     * 详细地址
     */
    private String detail;

    private Date ctime;

    private Date mtime;
}
