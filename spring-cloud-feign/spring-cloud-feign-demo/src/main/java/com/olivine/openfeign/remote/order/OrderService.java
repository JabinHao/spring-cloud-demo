package com.olivine.openfeign.remote.order;

import com.olivine.common.dto.base.CommonResponse;
import com.olivine.openfeign.remote.order.dto.OrderDTO;
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
//@FeignClient(value = "spring-cloud-order-service", url = "http://localhost:8081/order")
@FeignClient(value = "spring-cloud-consul-order-service")
public interface OrderService {

    @RequestMapping(value = "/order/get/by/uid/{uid}", method = RequestMethod.GET)
    CommonResponse<List<OrderDTO>> getByUid(@PathVariable("uid") String uid);
}
