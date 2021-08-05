package cn.tedu.sdorder;

import cn.tedu.sdorder.entity.Order;
import cn.tedu.sdorder.mapper.OrderMapper;
import cn.tedu.sdorder.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class SdOrderApplicationTests {
    @Autowired
    OrderMapper om;
    @Autowired
    OrderService os;


    @Test
    void contextLoads() {
    }

    @Test
    void test1() {
        System.out.println(om);
        om.create(
                new Order(1L,1L,1L,3, BigDecimal.valueOf(100),0));
    }
    @Test
    void test2() {
        System.out.println(os);
        os.create(
                new Order(1L,1L,1L,3, BigDecimal.valueOf(100),0));
    }

}
