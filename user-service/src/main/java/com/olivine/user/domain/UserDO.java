package com.olivine.user.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author jphao
 * @Date 2021/11/26 21:17
 * @Description
 */
@Data
public class UserDO {

    private Long id;
    private String uid;
    private String gender;
    private String name;
    private Date ctime;
    private Date mtime;
}
