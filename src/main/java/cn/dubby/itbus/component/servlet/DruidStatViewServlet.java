package cn.dubby.itbus.component.servlet;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Created by teeyoung on 2017/3/10.
 */
@WebServlet(
        name = "DruidStatView",
        urlPatterns = {"/druid/*"},
        initParams = {
                @WebInitParam(name = "resetEnable", value = "true"),
                @WebInitParam(name = "loginUsername", value = "dubby"),
                @WebInitParam(name = "loginPassword", value = "dubby"),
        })
public class DruidStatViewServlet extends StatViewServlet {

}
