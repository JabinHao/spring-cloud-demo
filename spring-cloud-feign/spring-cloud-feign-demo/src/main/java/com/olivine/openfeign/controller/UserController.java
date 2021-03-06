package com.olivine.openfeign.controller;

import com.olivine.common.dto.base.CommonResponse;
import com.olivine.openfeign.remote.order.OrderService;
import com.olivine.openfeign.remote.order.dto.OrderDTO;
import com.olivine.openfeign.remote.user.UserService;
import com.olivine.openfeign.remote.user.dto.UserDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author jphao
 * @Date 2021/12/14 16:25
 * @Description
 */
@RestController
@RequestMapping("feign")
public class UserController {

    private final UserService userService;

    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @RequestMapping(value = "/get/user/{uid}", method = RequestMethod.GET)
    CommonResponse<UserDTO> getUserInfo(@PathVariable("uid") String uid){

        final CommonResponse<UserDTO> info = userService.getUserInfo(uid);

        return info;
    }

    @RequestMapping(value = "/get/order/{uid}", method = RequestMethod.GET)
    public CommonResponse<List<OrderDTO>> getByUid(@PathVariable("uid") String uid){
        return orderService.getByUid(uid);
    }

}
