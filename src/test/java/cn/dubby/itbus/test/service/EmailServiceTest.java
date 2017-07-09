package cn.dubby.itbus.test.service;

import cn.dubby.itbus.service.EmailService;
import cn.dubby.itbus.test.BaseTest;
import org.junit.Ignore;
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

    private static String template = "<html><head>\n" +
            "<base target=\"_blank\">\n" +
            "<style type=\"text/css\">\n" +
            "::-webkit-scrollbar{ display: none; }\n" +
            "</style>\n" +
            "<style id=\"cloudAttachStyle\" type=\"text/css\">\n" +
            "#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}\n" +
            "</style>\n" +
            "\n" +
            "</head>\n" +
            "<body tabindex=\"0\" role=\"listitem\">\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"font-family:Microsoft Yahei, Hiragino Sans GB, Helvetica Neue, Arial, sans-serif;font-size:14px;color:#333;border:none;\">\n" +
            "  <tbody>\n" +
            "  <tr>\n" +
            "    <td colspan=\"3\" style=\"border:none;\">\n" +
            "      <div style=\"background-color:#007edf;padding:10px 20px;border:none;\">\n" +
            "        <a href=\"http://www.mtyun.com\" style=\"color:#fff;text-decoration:none;font-size:32px;outline:none;border:none;margin:0;padding:0;\">ITBus</a>\n" +
            "\n" +
            "      </div>\n" +
            "\n" +
            "    </td>\n" +
            "  </tr>\n" +
            "  <tr style=\"border:none;\">\n" +
            "    <td colspan=\"3\" style=\"background-color:#fafafa;padding:20px;padding-top:30px;border:none;\">\n" +
            "      <p style=\"margin:0;padding:0;text-align:center;font-size:20px;\">每周博客推荐</p>\n" +
            "      <div>\n" +
            "        <p style=\"margin:20px 0;padding:0;\">尊敬的<strong style=\"font-size:15px;\">ITBus用户</strong> :</p>\n" +
            "        <div style=\"margin:0;padding:0;text-indent:32px;line-height:2\">\n" +
            "        \n" +
            "            <p>感谢您订阅ITBus的文章推送，一下是本周最佳文章，感谢您的阅读，祝您生活愉快，工作顺利：</p>\n" +
            "        \n" +
            "        <ul style=\"list-style-type:none;\">\n" +
            "\t\t<li> <a href=\"http://www.itbus.tech/detail.html?id=8736\">使用Spring Cloud和Docker构建微服务(一）</a> </li>\n" +
            "\t\t<br>\n" +
            "\t\t<li> <a href=\"http://www.itbus.tech/detail.html?id=8737\">使用Spring Cloud和Docker构建微服务(二）</a> </li>\n" +
            "\t\t<br>\n" +
            "\t\t<li> <a href=\"http://www.itbus.tech/detail.html?id=8738\">Docker入门六部曲——Swarm</a> </li>\n" +
            "\t\t<br>\n" +
            "\t\t<li> <a href=\"http://www.itbus.tech/detail.html?id=8739\">Docker入门六部曲——Stack</a> </li>\n" +
            "\t</ul>\n" +
            "\n" +
            "        </div>\n" +
            "        <p style=\"margin:20px 0 30px;padding:0;font-weight:bold\">ITBus</p>\n" +
            "      </div>\n" +
            "    </td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td style=\"background-color:#007edf;color:#fff;font-size:12px;line-height:1.5;border:none;\" width=\"120px\">\n" +
            "        <p style=\"width:80px;margin:0;padding:8px 20px 10px;\">微信扫码关注<br><img src=\"http://www.itbus.tech/upload/1499588005784ITBusSmall.jpg\" alt=\"ITBusTech\" width=\"80px\" style=\"margin:0;padding:0;border:none;\">\n" +
            "       \n" +
            "    </td>\n" +
            "    <td style=\"background-color:#007edf;padding:10px;color:#fff;font-size:12px;line-height:2.5;border:none;\"><div class=\"ab\" style=\"\">\n" +
            "      <table class=\"ab\" style=\"color:#fff;\">\n" +
            "        \n" +
            "          <tbody><tr>\n" +
            "            <td align=\"right\" style=\"font-size:12px;\">ITBus官网：</td>\n" +
            "            <td><a style=\"display:inline-block;padding:1px 10px;background-color:#007edf;color:#fff;outline:none;border:none;border:none;font-size:12px;\" href=\"http://www.itbus.tech/\">http://www.itbus.tech/</a></td>\n" +
            "          </tr>\n" +
            "        \n" +
            "          <tr>\n" +
            "            <td align=\"right\" style=\"font-size:12px;\">客服电话：</td>\n" +
            "            <td><a style=\"display:inline-block;padding:1px 10px;background-color:#007edf;color:#fff;outline:none;border:none;border:none;font-size:12px;\" title=\"电话 18217112651\" href=\"tel:18217112651\">18217112651</a></td>\n" +
            "          </tr>\n" +
            "        \n" +
            "          <tr>\n" +
            "            <td align=\"right\" style=\"font-size:12px;\">客服QQ：</td>\n" +
            "            <td><a style=\"display:inline-block; padding: 1px 10px; color: rgb(255, 255, 255); text-decoration: none; outline: none; background-color: rgb(0, 126, 223);font-size:12px;\" title=\"QQ 1778520607\">1778520607</a></td>\n" +
            "          </tr>\n" +
            "        \n" +
            "          <tr>\n" +
            "            <td align=\"right\" style=\"font-size:12px;\">客服邮箱：</td>\n" +
            "            <td><a style=\"display:inline-block;padding:1px 10px;background-color:#007edf;color:#fff;outline:none;border:none;border:none;font-size:12px;\" title=\"yang_zheng1994@163.com\" href=\"mailto:yang_zheng1994@163.com\">yang_zheng1994@163.com</a>\n" +
            "          </td></tr>\n" +
            "      </tbody></table>\n" +
            "    </div></td>\n" +
            "    \n" +
            "    <td style=\"background-color:#007edf;color:#fff;font-size:12px;line-height:1.5;border:none;\">\n" +
            "      <a href=\"#\" style=\"color:#fff;text-decoration:none;outline:none;border:none;\">退订此类邮件</a>\n" +
            "    </td>\n" +
            "    \n" +
            "  </tr>\n" +
            "</tbody>\n" +
            "</table> \n" +
            "\n" +
            "<style type=\"text/css\">\n" +
            "body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}\n" +
            "td, input, button, select, body{font-family:Helvetica, 'Microsoft Yahei', verdana}\n" +
            "pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%}\n" +
            "th,td{font-family:arial,verdana,sans-serif;line-height:1.666}\n" +
            "img{ border:0}\n" +
            "header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}\n" +
            "blockquote{margin-right:0px}\n" +
            "</style>\n" +
            "\n" +
            "<style id=\"ntes_link_color\" type=\"text/css\">a,td a{color:#064977}</style>\n" +
            "\n" +
            "<audio controls=\"controls\" style=\"display: none;\"></audio></body><style type=\"text/css\">#yddContainer{display:block;font-family:Microsoft YaHei;position:relative;width:100%;height:100%;top:-4px;left:-4px;font-size:12px;border:1px solid}#yddTop{display:block;height:22px}#yddTopBorderlr{display:block;position:static;height:17px;padding:2px 28px;line-height:17px;font-size:12px;color:#5079bb;font-weight:bold;border-style:none solid;border-width:1px}#yddTopBorderlr .ydd-sp{position:absolute;top:2px;height:0;overflow:hidden}.ydd-icon{left:5px;width:17px;padding:0px 0px 0px 0px;padding-top:17px;background-position:-16px -44px}.ydd-close{right:5px;width:16px;padding-top:16px;background-position:left -44px}#yddKeyTitle{float:left;text-decoration:none}#yddMiddle{display:block;margin-bottom:10px}.ydd-tabs{display:block;margin:5px 0;padding:0 5px;height:18px;border-bottom:1px solid}.ydd-tab{display:block;float:left;height:18px;margin:0 5px -1px 0;padding:0 4px;line-height:18px;border:1px solid;border-bottom:none}.ydd-trans-container{display:block;line-height:160%}.ydd-trans-container a{text-decoration:none;}#yddBottom{position:absolute;bottom:0;left:0;width:100%;height:22px;line-height:22px;overflow:hidden;background-position:left -22px}.ydd-padding010{padding:0 10px}#yddWrapper{color:#252525;z-index:10001;background:url(chrome-extension://eopjamdnofihpioajgfdikhhbobonhbb/ab20.png);}#yddContainer{background:#fff;border-color:#4b7598}#yddTopBorderlr{border-color:#f0f8fc}#yddWrapper .ydd-sp{background-image:url(chrome-extension://eopjamdnofihpioajgfdikhhbobonhbb/ydd-sprite.png)}#yddWrapper a,#yddWrapper a:hover,#yddWrapper a:visited{color:#50799b}#yddWrapper .ydd-tabs{color:#959595}.ydd-tabs,.ydd-tab{background:#fff;border-color:#d5e7f3}#yddBottom{color:#363636}#yddWrapper{min-width:250px;max-width:400px;}</style></html>";

    @Ignore
    public void test() {
//        try {
//            // 我自己的QQ邮箱
//            emailService.sendEmail("1778520607@qq.com", "测试", "测试内容");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
    }

    @Ignore
    public void testNotice() {
//        try {
//            // 我自己的QQ邮箱
//            emailService.sendEmail("yang_zheng1994@163.com", "ITBus", template);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
    }

}
