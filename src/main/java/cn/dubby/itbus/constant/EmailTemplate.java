package cn.dubby.itbus.constant;

/**
 * Created by yangzheng03 on 2017/4/25.
 */
public class EmailTemplate {

    public static final String THANKS_WRITE_EMAIL_CONTENT = "感谢您在dubby.cn上发表文章<br/><br>"
            + "<b>您的文章ID:<br/>【%d】<br/>Ticket:<br/>【%s】</b><br/><br/>"
            + "<a href='http://www.dubby.cn/'>爱学习的人都在dubby.cn</a>";

    public static final String THANKS_WRITE_EMAIL_SUBJECT = "感谢您在dubby.cn上发表文章";

    public static final String NOTICE_MASTER_SUBJECT = "dubby通知";

    public static final String NOTICE_MASTER_RECIPIENT = "yang_zheng1994@163.com";

    public static final String THANKS_REGISTER_EMAIL_CONTENT = "您的登录账号为:" +
            "<b>%s</b><br/>" +
            "衷心的希望您可以在dubby.cn上有所学习和收获" +
            "<br/><br/><a href='http://www.dubby.cn/'>爱学习的人都在dubby.cn</a>";

    public static final String THANKS_REGISTER_EMAIL_SUBJECT = "感谢您在dubby.cn上注册账号";

    public static final String RESET_PASSWORD_EMAIL_CONTENT = "您的重置之后的密码为:" +
            "<b>%s</b><br/>" +
            "衷心的希望您可以在dubby.cn上有所学习和收获" +
            "<br/><br/><a href='http://www.dubby.cn/'>爱学习的人都在dubby.cn</a>";

    public static final String RESET_PASSWORD_EMAIL_SUBJECT = "dubby.cn找回密码";

}
