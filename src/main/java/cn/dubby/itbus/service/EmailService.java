package cn.dubby.itbus.service;

import cn.dubby.itbus.bean.EmailWithBLOBs;
import cn.dubby.itbus.constant.EmailTemplate;
import cn.dubby.itbus.mapper.EmailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

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

    @Autowired
    private EmailMapper emailMapper;

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

    @Async(value = "emailTaskExecutor")
    public void sendWriteThanksEmail(String recipient, int busId, String ticket) {
        EmailWithBLOBs email = new EmailWithBLOBs();
        email.setRecipient(recipient);
        email.setSubject(EmailTemplate.THANKS_WRITE_EMAIL_SUBJECT);
        email.setContent(String.format(EmailTemplate.THANKS_WRITE_EMAIL_CONTENT, busId, ticket));
        email.setStatus(1);

        try {
            sendEmail(email.getRecipient(), email.getSubject(), email.getContent());
            sendEmail(EmailTemplate.NOTICE_MASTER_RECIPIENT, EmailTemplate.NOTICE_MASTER_SUBJECT, "新增文章:" + busId);
        } catch (Exception e) {
            logger.error("send email error", e);
            email.setStatus(-1);
        }
        emailMapper.insertSelective(email);
    }

    @Async(value = "emailTaskExecutor")
    public void sendRegisterThanksEmail(String recipient) {
        EmailWithBLOBs email = new EmailWithBLOBs();
        email.setRecipient(recipient);
        email.setSubject(EmailTemplate.THANKS_REGISTER_EMAIL_SUBJECT);
        email.setContent(String.format(EmailTemplate.THANKS_REGISTER_EMAIL_CONTENT, recipient));
        email.setStatus(1);

        try {
            sendEmail(email.getRecipient(), email.getSubject(), email.getContent());
            sendEmail(EmailTemplate.NOTICE_MASTER_RECIPIENT, EmailTemplate.NOTICE_MASTER_SUBJECT, "注册用户:" + recipient);
        } catch (Exception e) {
            logger.error("send email error", e);
            email.setStatus(-1);
        }
        emailMapper.insertSelective(email);
    }

    @Async(value = "emailTaskExecutor")
    public void sendResetPasswordEmail(String recipient, String password) {
        EmailWithBLOBs email = new EmailWithBLOBs();
        email.setRecipient(recipient);
        email.setSubject(EmailTemplate.RESET_PASSWORD_EMAIL_SUBJECT);
        email.setContent(String.format(EmailTemplate.RESET_PASSWORD_EMAIL_CONTENT, password));
        email.setStatus(1);

        try {
            sendEmail(email.getRecipient(), email.getSubject(), email.getContent());
            sendEmail(EmailTemplate.NOTICE_MASTER_RECIPIENT, EmailTemplate.NOTICE_MASTER_SUBJECT, "找回密码:" + recipient);
        } catch (Exception e) {
            logger.error("send email error", e);
            email.setStatus(-1);
        }
        emailMapper.insertSelective(email);
    }
}
