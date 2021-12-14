package com.olivine.openfeign.remote.order;

import com.olivine.common.dto.base.CommonResponse;
import com.olivine.openfeign.remote.order.dto.OrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @Author jphao
 * @Date 2021/12/14 15:37
 * @Description
 */
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void getByUidTest(){
        String uid = "202102110631150000620787";
        final CommonResponse<List<OrderDTO>> response = orderService.getByUid(uid);

        Assert.isTrue(response.getCode().equals(200), String.format("fail to get orders, uid: %s, message: %s", uid, response.getMessage()));
    }

}
