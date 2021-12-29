package com.olivine.order.utils.convert;

import com.olivine.order.domain.OrderDO;
import com.olivine.order.dto.order.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author jphao
 * @Date 2021/11/26 15:43
 * @Description
 */
public class OrderConvertUtil {

    public static OrderDTO doToDTO(OrderDO orderDO){

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderDO, orderDTO);
        return orderDTO;
    }
    public static List<OrderDTO> doToDTO(List<OrderDO> orderDOList){

        final List<OrderDTO> orderDTOList = orderDOList.parallelStream()
                .map(OrderConvertUtil::doToDTO)
                .collect(Collectors.toList());

        return orderDTOList;
    }

    public static OrderDO dtoToDO(OrderDTO orderDTO){
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderDTO, orderDO);
        return orderDO;
    }
}
