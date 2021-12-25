package com.olivine.order.mapper;

import com.olivine.order.domain.OrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author jphao
 * @Date 2021/11/26 0:24
 * @Description
 */
@Mapper
public interface OrderMapper {

    OrderDO findByOrderId(String orderId);

    List<OrderDO> findByUid(String uid);

    int insertOrder(OrderDO orderDO);
}
