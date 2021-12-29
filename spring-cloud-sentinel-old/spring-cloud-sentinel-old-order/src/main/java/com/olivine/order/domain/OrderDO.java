package com.olivine.order.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author jphao
 * @date 2021/11/25 23:52
 * @description order information
 */
@Data
public class OrderDO {

    private Long id;
    private String uid;
    private Integer count;
    private String orderId;
    private String tradeId;
    private String productId;
    private Double totalPrice;
    private String address;

    // create time
    private Date ctime;
    // pay time
    private Date ptime;
    // deliver time
    private Date dtime;
    private Date mtime;
}
