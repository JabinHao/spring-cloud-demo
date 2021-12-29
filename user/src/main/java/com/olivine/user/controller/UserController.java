package com.olivine.user.controller;

import com.olivine.user.dto.UserDTO;
import com.olivine.user.dto.base.CommonResponse;
import com.olivine.user.enums.ApiCode;
import com.olivine.user.service.UserService;
import org.springframework.web.bind.annotation.*;

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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/get/{uid}", method = RequestMethod.GET)
    public CommonResponse<UserDTO> getUserByUid(@PathVariable("uid") String uid){

        final UserDTO userDTO = userService.findByUid(uid);
        if (userDTO == null)
            return new CommonResponse<>(ApiCode.FAILED);
        return CommonResponse.success(userDTO);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public CommonResponse<List<UserDTO>> getAllUsers(){
        final List<UserDTO> userDTOList = userService.findAll();

        return CommonResponse.success(userDTOList);
    }

    @RequestMapping(value = "/get/info/{uid}", method = RequestMethod.GET)
    public CommonResponse<UserDTO> getUserInfo(@PathVariable("uid") String uid){

        final UserDTO userDTO = userService.findInfoByUid(uid);
        if (userDTO == null)
            return new CommonResponse<>(ApiCode.FAILED);

        return CommonResponse.success(userDTO);
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
