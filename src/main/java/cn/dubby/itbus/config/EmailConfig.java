package cn.dubby.itbus.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by yangzheng03 on 2017/4/23.
 */
@Configuration
public class EmailConfig {

    private static final Logger logger = LoggerFactory.getLogger(EmailConfig.class);


    @Autowired
    private RedisTemplate<String, String> template;

    @Bean(name = "username4Email")
    public String username4Email() {
        return template.opsForValue().get("username4Email");
    }

    @Bean(name = "password4Email")
    public String password4Email() {
        return template.opsForValue().get("password4Email");
    }

    @Bean(name = "mailTransportProtocol")
    public String mailTransportProtocol() {
        return template.opsForValue().get("mailTransportProtocol");
    }

    @Bean(name = "mailSmtpHost")
    public String mailSmtpHost() {
        return template.opsForValue().get("mailSmtpHost");
    }

    @Bean(name = "mailSmtpPort")
    public String mailSmtpPort() {
        return template.opsForValue().get("mailSmtpPort");
    }

}
