package cn.tedu.sdeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SdEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdEurekaApplication.class, args);
    }

}
