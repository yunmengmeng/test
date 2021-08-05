package cn.tedu.sdaccount;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("cn.tedu.sdaccount.mapper")
public class SdAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(SdAccountApplication.class, args);
    }

}
