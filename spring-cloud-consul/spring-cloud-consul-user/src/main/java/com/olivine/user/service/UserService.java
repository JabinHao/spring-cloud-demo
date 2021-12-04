package com.olivine.user.service;

import com.olivine.user.dto.UserDTO;

import java.util.List;

/**
 * @Author jphao
 * @Date 2021/11/26 18:27
 * @Description
 */
public interface UserService {

    UserDTO findByUid(String uid);

    UserDTO findInfo(String uid);

    void updateByUid(UserDTO userDTO);

    int saveUser(UserDTO userDTO);

    int deleteByUid(String uid);

    List<UserDTO> findAll();
}
