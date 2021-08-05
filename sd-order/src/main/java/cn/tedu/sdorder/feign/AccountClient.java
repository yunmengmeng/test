package cn.tedu.sdorder.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "sd-account")
public interface AccountClient {
    @GetMapping("/decrease")
    String decrease(@RequestParam Long userId, @RequestParam BigDecimal money);
}
