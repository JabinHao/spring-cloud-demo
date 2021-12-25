package com.olivine.feign.client.order;

import com.olivine.common.dto.base.CommonResponse;
import com.olivine.feign.client.order.fallback.OrderClientFallbackFactory;
import com.olivine.feign.pojo.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author jphao
 * @Date 2021/12/14 0:32
 * @Description
 */
@FeignClient(value = "spring-cloud-sentinel-old-order-service", fallbackFactory = OrderClientFallbackFactory.class)
public interface OrderClient {

    /**
     * get all orders of a user
     * @param uid user id
     * @return orders
     */
    @RequestMapping(value = "/order/get/by/uid/{uid}", method = RequestMethod.GET)
    CommonResponse<List<OrderDTO>> getByUid(@PathVariable("uid") String uid);

    /**
     * save order by uid
     * @param orderDTO order info
     * @return none
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    CommonResponse<Void> saveOrder(@RequestBody OrderDTO orderDTO);
}
