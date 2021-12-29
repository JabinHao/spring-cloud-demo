package com.olivine.order.service.impl;

import com.olivine.order.domain.OrderDO;
import com.olivine.order.dto.order.OrderDTO;
import com.olivine.order.mapper.OrderMapper;
import com.olivine.order.service.OrderService;
import com.olivine.order.utils.convert.OrderConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author jphao
 * @Date 2021/11/26 15:27
 * @Description
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public OrderDTO getByOrderId(String orderId) {
        final OrderDO orderDO = orderMapper.findByOrderId(orderId);
        if (orderDO == null)
            return null;
        return OrderConvertUtil.doToDTO(orderDO);
    }

    @Override
    public List<OrderDTO> getByUid(String uid) {
        final List<OrderDO> orderDOList = orderMapper.findByUid(uid);
        if (orderDOList == null || orderDOList.size() == 0)
            return null;
       return OrderConvertUtil.doToDTO(orderDOList);
    }

    @Override
    public int saveOrder(OrderDTO orderDTO) {
        OrderDO orderDO = OrderConvertUtil.dtoToDO(orderDTO);
        int id = orderMapper.insertOrder(orderDO);
        return id;
    }
}
