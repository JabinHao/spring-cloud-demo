package com.olivine.openfeign.remote.user;

import com.olivine.common.dto.base.CommonResponse;
import com.olivine.openfeign.remote.user.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @Author jphao
 * @Date 2021/12/14 15:37
 * @Description
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserInfoTest(){

        String uid = "202102110631150000620787";
        final CommonResponse<UserDTO> response = userService.getUserInfo(uid);

        Assert.isTrue(response.getCode().equals(200), "fail to get user info: " + response.getMessage());
    }
}
