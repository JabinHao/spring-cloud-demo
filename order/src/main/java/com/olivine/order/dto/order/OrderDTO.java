package com.olivine.order.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author jphao
 * @Date 2021/11/26 0:06
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

    private String address;
    // create time
    private Date ctime;
    // pay time
    private Date ptime;
    // deliver time
    private Date dtime;

    private Date mtime;
}
