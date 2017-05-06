package cn.dubby.itbus.constant;

/**
 * Created by yangzheng03 on 2017/4/25.
 */
public class EmailTemplate {

    public static final String THANKS_WRITE_EMAIL_CONTENT = "感谢您在ITBus上发表文章<br/><br>"
            + "<b>您的文章ID:<br/>【%d】<br/>Ticket:<br/>【%s】</b><br/><br/>"
            + "<a href='http://www.itbus.tech/'>爱学习的人都在ITBus</a>";

    public static final String THANKS_WRITE_EMAIL_SUBJECT = "感谢您在ITBus上发表文章";

    public static final String NOTICE_MASTER_SUBJECT = "ITBus通知";

    public static final String NOTICE_MASTER_RECIPIENT = "yang_zheng1994@163.com";

    public static final String THANKS_REGISTER_EMAIL_CONTENT = "您的登录账号为:" +
            "<b>%s</b><br/>" +
            "衷心的希望您可以在ITBus上有所学习和收获" +
            "<br/><br/><a href='http://www.itbus.tech/'>爱学习的人都在ITBus</a>";

    public static final String THANKS_REGISTER_EMAIL_SUBJECT = "感谢您在ITBus上注册账号";

}
