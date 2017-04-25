package cn.dubby.itbus.test.service;

import cn.dubby.itbus.service.EmailService;
import cn.dubby.itbus.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * Created by yangzheng03 on 2017/4/25.
 */
public class EmailServiceTest extends BaseTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void test() {
        try {
            // 我自己的QQ邮箱
            emailService.sendEmail("1778520607@qq.com", "测试", "测试内容");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
