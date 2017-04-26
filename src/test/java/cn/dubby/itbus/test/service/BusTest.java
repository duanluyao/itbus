package cn.dubby.itbus.test.service;

import cn.dubby.itbus.service.BusService;
import cn.dubby.itbus.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yangzheng03 on 2017/4/26.
 */
public class BusTest extends BaseTest {

    @Autowired
    private BusService busService;

    @Test
    public void create() {
        for (int i = 0; i < 100000; ++i) {
            busService.save(16, "Test-new" + i, "Content-new" + i, "yang_zheng1994@163.com");
            System.out.println(i);
        }
    }

}
