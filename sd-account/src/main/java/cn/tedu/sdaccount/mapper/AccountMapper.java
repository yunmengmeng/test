package cn.tedu.sdaccount.mapper;

import cn.tedu.sdaccount.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

public interface AccountMapper extends BaseMapper<Account> {
    void decrease(Long userId, BigDecimal money);
}
