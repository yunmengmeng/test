package cn.tedu.sdstorage;

import cn.tedu.sdstorage.mapper.StorageMapper;
import cn.tedu.sdstorage.service.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SdStorageApplicationTests {
    @Autowired
    StorageMapper sm;
    @Autowired
    StorageService ss;

    @Test
    void contextLoads() {
    }

    @Test
    void test1() {
        System.out.println(sm);
        sm.decrease(1L, 1);
    }
    @Test
    void test2() throws Exception {
        System.out.println(ss);
        ss.decrease(1L, 1);
    }
}
