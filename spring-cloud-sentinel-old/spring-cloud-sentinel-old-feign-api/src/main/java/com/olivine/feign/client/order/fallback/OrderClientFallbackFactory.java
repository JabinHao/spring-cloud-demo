package com.olivine.feign.client.order.fallback;

import com.olivine.common.dto.base.CommonResponse;
import com.olivine.feign.client.order.OrderClient;
import com.olivine.feign.pojo.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author jphao
 * @Date 19:24 2021/12/24
 * @Description
 */
@Slf4j
public class OrderClientFallbackFactory implements FallbackFactory<OrderClient> {
    @Override
    public OrderClient create(Throwable cause) {
        OrderClient fallbackOrderClient = new OrderClient() {
            @Override
            public CommonResponse<List<OrderDTO>> getByUid(String uid) {
                log.error("查询订单异常", cause);
                return CommonResponse.success(new ArrayList<>());
            }

            @Override
            public CommonResponse<Void> saveOrder(OrderDTO orderDTO) {
                log.error("保存订单异常", cause);
                return CommonResponse.success();
            }
        };
        log.warn("OrderClientFallbackFactory: 触发 sentinel 熔断");
        return fallbackOrderClient;
    }
}
