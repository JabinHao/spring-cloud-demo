package com.olivine.user.remote.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author jphao
 * @Date 2021/11/28 19:30
 * @Description
 */
@Data
public class OrderDTO {

    @JsonProperty("order_id")
    private String orderId;

    private String uid;

    @JsonProperty("product_id")
    private String productId;

    private Integer count;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("trade_id")
    private String tradeId;

    /**
     * 收货地址
     */
    private String address;

    // create time
    private Date ctime;
    // pay time
    private Date ptime;
    // deliver time
    private Date dtime;
}
