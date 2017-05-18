package org.wx.weixiao.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.wx.weixiao.servlet.conf.AppConfig;
import org.wx.weixiao.util.AppConfigUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求处理的核心类
 *
 * @author
 * @date
 */
public class AllDispatchServlet extends HttpServlet {

    private static final long serialVersionUID = -2791585436709500565L;
    private AppConfig appConfig;
    private Handler handler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        handler = (Handler) ctx.getBean("AllDispatchHandler");
        appConfig = new AppConfig();
        appConfig.setApiKey(config.getInitParameter("apiKey"));
        appConfig.setApiSecret(config.getInitParameter("apiSecret"));
        appConfig.setToken(config.getInitParameter("token"));
        AppConfigUtil.put(AppConfigUtil.ALLDISPATCH, appConfig);
    }

    /**
     * 请求校验（确认请求来自微信服务器）
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * 处理微信服务器发来的消息
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handler.handle(request, response, appConfig);
    }


}

