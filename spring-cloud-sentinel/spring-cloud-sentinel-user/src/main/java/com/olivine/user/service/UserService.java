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

    void updateByUid(UserDTO userDTO);

    void saveUser(UserDTO userDTO);

    void deleteByUid(String uid);

    List<UserDTO> findAll();

    UserDTO findInfoByUid(String uid);
}
