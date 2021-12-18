package com.olivine.user.mapper;

import com.olivine.user.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author jphao
 * @Date 2021/11/26 18:26
 * @Description
 */
public interface UserMapper {
    int deleteByUid(String uid);

    int insertUser(UserDO userDO);

    UserDO selectByUid(String uid);

    int updateByUid(UserDO userDO);

    List<UserDO> selectAll();
}
