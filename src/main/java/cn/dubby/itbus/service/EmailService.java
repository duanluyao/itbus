package cn.dubby.itbus.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by yangzheng03 on 2017/4/23.
 */
@Service
public class EmailService {

    @Resource(name = "username4Email")
    private String username4Email;

    @Resource(name = "password4Email")
    private String password4Email;

    @Resource(name = "mailTransportProtocol")
    private String mailTransportProtocol;

    @Resource(name = "mailSmtpHost")
    private String mailSmtpHost;

    @Resource(name = "mailSmtpPort")
    private String mailSmtpPort;

    public void sendEmail(String recipient, String subject, String content) throws UnsupportedEncodingException, MessagingException {
        // 1. 创建一封邮件
        Properties props = new Properties();                // 用于连接邮件服务器的参数配置（发送邮件时才需要用到）
        props.put("username", username4Email);
        props.put("password", password4Email);
        props.put("mail.transport.protocol", mailTransportProtocol);
        props.put("mail.smtp.host", mailSmtpHost);
        props.put("mail.smtp.port", mailSmtpPort);
        Session session = Session.getDefaultInstance(props); // 根据参数配置，创建会话对象（为了发送邮件准备的）
        MimeMessage message = new MimeMessage(session);     // 创建邮件对象

        // 2. From: 发件人
        message.setFrom(new InternetAddress(username4Email, username4Email, "UTF-8"));

        // 3. To: 收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient, recipient, "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject(subject, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");

        // 6. 设置显示的发件时间
        message.setSentDate(new Date());

        // 7. 保存前面的设置
        message.saveChanges();

        Transport transport = session.getTransport("smtp");
        transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("username"), props.getProperty("password"));

        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
    }

}
