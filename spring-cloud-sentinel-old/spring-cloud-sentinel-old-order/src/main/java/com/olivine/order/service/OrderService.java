package com.olivine.order.service;

import com.olivine.order.dto.order.OrderDTO;

import java.util.List;

/**
 * @Author jphao
 * @Date 2021/11/26 15:27
 * @Description
 */
public interface OrderService {

    OrderDTO getByOrderId(String orderId);

    List<OrderDTO> getByUid(String uid);

    int saveOrder(OrderDTO orderDTO);
}
