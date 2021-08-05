package cn.tedu.sdorder.controller;

import cn.tedu.sdorder.entity.Order;
import cn.tedu.sdorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderController {
    @Autowired
    OrderService orderService;


    /*
    用户用这个路径进行访问：
    http://localhost:8083/create?userId=1&productId=1&count=10&money=100
     */
    @GetMapping("/create")
    public String create(Order order) {
        orderService.create(order);
        return "create order success";
    }
}
