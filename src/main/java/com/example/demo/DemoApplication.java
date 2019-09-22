package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
/**
 * 扫描包，这样下面的文件在使用注解时会被Spring扫描到
 */
@ComponentScan(basePackages = {
        "com.example.demo.controller",
        "com.example.demo.entity"
})
@MapperScan("com.example.demo.mapper")      //扫描mapper包，要不然后期会出错
public class DemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);
        SpringApplication springApplication=new SpringApplication(DemoApplication.class);
        springApplication.run(args);
    }

}
