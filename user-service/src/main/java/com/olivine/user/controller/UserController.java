package com.olivine.user.controller;

import com.olivine.user.dto.UserDTO;
import com.olivine.user.dto.base.CommonResponse;
import com.olivine.user.enums.ApiCode;
import com.olivine.user.remote.order.dto.OrderDTO;
import com.olivine.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author jphao
 * @Date 2021/11/26 18:27
 * @Description
 */
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final RestTemplate restTemplate;

    public UserController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/get/{uid}", method = RequestMethod.GET)
    public CommonResponse<UserDTO> getUserByUid(@PathVariable("uid") String uid){

        final UserDTO userDTO = userService.findByUid(uid);
        if (userDTO == null)
            return new CommonResponse<>(ApiCode.FAILED);
        return CommonResponse.success(userDTO);
    }

    @RequestMapping(value = "/get/info/{uid}", method = RequestMethod.GET)
    public CommonResponse<UserDTO> getUserInfo(@PathVariable("uid") String uid){

        final UserDTO userDTO = userService.findByUid(uid);
        if (userDTO == null)
            return new CommonResponse<>(ApiCode.FAILED);

        String url = "http://localhost:8081/order/get/by/uid/" + uid;

        final CommonResponse response = restTemplate.getForObject(url, CommonResponse.class);
        assert response != null;
        final Object data = response.getData();
        if (data != null){
            List<OrderDTO> orderDTOList = (List<OrderDTO>) data;
            userDTO.setOrders(orderDTOList);
        }

        return CommonResponse.success(userDTO);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public CommonResponse<List<UserDTO>> getAllUsers(){
        final List<UserDTO> userDTOList = userService.findAll();

        return CommonResponse.success(userDTOList);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResponse<Void> saveUser(@RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);
        return CommonResponse.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public CommonResponse<UserDTO> updateUser(@RequestBody UserDTO userDTO){

        final UserDTO user = userService.findByUid(userDTO.getUid());
        if (user == null)
            return new CommonResponse<>(ApiCode.FAILED);

        userService.updateByUid(userDTO);
        final UserDTO updatedUserDTO = userService.findByUid(userDTO.getUid());
        return CommonResponse.success(updatedUserDTO);
    }

    @RequestMapping(value = "/delete/{uid}", method = RequestMethod.DELETE)
    public CommonResponse<Void> deleteUser(@PathVariable("uid") String uid){
        userService.deleteByUid(uid);
        return CommonResponse.success();
    }
}
