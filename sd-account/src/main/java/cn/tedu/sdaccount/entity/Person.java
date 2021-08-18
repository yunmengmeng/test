package cn.tedu.sdaccount.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author huxin
 * @since 2021/8/17
 */
@Component
@ConfigurationProperties(prefix = "person")
@RefreshScope
@Data
public class Person {
    private String name;
    private Integer age;

    @PostConstruct
    public void init() {
        System.out.println(this);
    }
}
