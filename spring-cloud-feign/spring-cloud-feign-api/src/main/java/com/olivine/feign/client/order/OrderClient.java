package com.olivine.feign.client.order;

import com.olivine.common.dto.base.CommonResponse;
import com.olivine.feign.pojo.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author jphao
 * @Date 2021/12/14 0:32
 * @Description
 */
@FeignClient(value = "spring-cloud-consul-order-service")
public interface OrderClient {

    @RequestMapping(value = "/order/get/by/uid/{uid}", method = RequestMethod.GET)
    CommonResponse<List<OrderDTO>> getByUid(@PathVariable("uid") String uid);
}
