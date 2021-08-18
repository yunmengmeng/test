package cn.tedu.sdorder.feign;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author huxin
 * @since 2021/8/18
 */
@Component
public class AccountClientHystrix implements AccountClient {
    @Override
    public String decrease(Long userId, BigDecimal money) {
        return "invoke timeout";
    }
}
