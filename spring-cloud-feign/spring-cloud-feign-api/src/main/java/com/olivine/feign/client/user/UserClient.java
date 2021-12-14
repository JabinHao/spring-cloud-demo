package com.olivine.feign.client.user;

import com.olivine.common.dto.base.CommonResponse;
import com.olivine.feign.pojo.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author jphao
 * @Date 2021/12/14 0:34
 * @Description
 */
@FeignClient(value = "spring-cloud-user-service")
public interface UserClient {

    @RequestMapping(value = "/user/get/info/{uid}", method = RequestMethod.GET)
    CommonResponse<UserDTO> getUserInfo(@PathVariable("uid") String uid);
}
