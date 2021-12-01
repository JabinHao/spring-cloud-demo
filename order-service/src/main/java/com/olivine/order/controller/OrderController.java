package com.olivine.order.controller;

import com.olivine.order.dto.base.CommonResponse;
import com.olivine.order.dto.order.OrderDTO;
import com.olivine.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.olivine.order.enums.ApiCode.FAILED;

/**
 * @Author jphao
 * @Date 2021/11/26 16:01
 * @Description
 */
@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResponse<Void> saveOrder(@RequestBody OrderDTO orderDTO) {
        orderService.saveOrder(orderDTO);

        return CommonResponse.success();
    }

    @RequestMapping(value = "/get/by/uid/{uid}", method = RequestMethod.GET)
    public CommonResponse<List<OrderDTO>> getByUid(@PathVariable("uid") String uid){
        final List<OrderDTO> orderDTOList = orderService.getByUid(uid);
        if (orderDTOList == null)
            return new  CommonResponse<>(FAILED);
        return CommonResponse.success(orderDTOList);
    }

    @RequestMapping(value = "/get/by/order_id/{order_id}", method = RequestMethod.GET)
    public CommonResponse<OrderDTO> getByOrderId(@PathVariable("order_id") String orderId) {
        final OrderDTO orderDTO = orderService.getByOrderId(orderId);
        if (orderDTO == null)
            return new CommonResponse<>(FAILED);
        return CommonResponse.success(orderDTO);
    }

}
