package cn.tedu.sdaccount;

import cn.tedu.sdaccount.mapper.AccountMapper;
import cn.tedu.sdaccount.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class SdAccountApplicationTests {
    @Autowired
    DataSource ds;
    @Autowired
    AccountMapper am;
    @Autowired
    AccountService as;

    @Test
    void contextLoads() {
    }

    @Test
    void test1() throws Exception {
        System.out.println(ds);

        Connection con = ds.getConnection();
        System.out.println(con);
    }

    @Test
    void test2() throws Exception {
        System.out.println(am);

        am.decrease(1L, BigDecimal.valueOf(1));
    }

    @Test
    void test3() throws Exception {
        System.out.println(as);

        as.decrease(1L, BigDecimal.valueOf(1));
    }

}
