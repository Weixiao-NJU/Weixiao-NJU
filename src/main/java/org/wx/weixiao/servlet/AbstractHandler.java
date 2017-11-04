package org.wx.weixiao.servlet;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.wx.weixiao.message.resp.RespMessage;
import org.wx.weixiao.servlet.conf.AppConfig;
import org.wx.weixiao.util.ErrorCodeUtil;
import org.wx.weixiao.util.NameUtil;
import org.wx.weixiao.util.SignUtil;
import org.wx.weixiao.util.TypeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Daniel on 2016/12/19.
 */
public abstract class AbstractHandler implements Handler {
    static Logger logger = Logger.getLogger(Handler.class);

    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AppConfig appConfig) throws ServletException, IOException {
        Util.analyseParameters(request);
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String type = request.getParameter("type");
        String msg = null;
        switch (type) {
            case TypeUtil.OPEN:
                msg = open(request, appConfig);
                break;
            case TypeUtil.KEYWORD:
                msg = keyword(request, appConfig);
                break;
            case TypeUtil.CLOSE:
                msg = close(request, appConfig);
                break;
            case TypeUtil.TRIGGER:
                msg = trigger(request, response, appConfig);
                break;
            case TypeUtil.CONFIG:
                msg = config(request, appConfig);
                break;
            case TypeUtil.MONITOR:
                msg = monitor(request, appConfig);
                break;
            default:
                msg = other(request, appConfig);
                break;
        }
        logger.info("RETURN MESSAGE :" + msg);
        response.getWriter().println(msg);
        response.getWriter().flush();

    }

    /**
     * 应用开启
     *
     * @param request
     */
    public String open(HttpServletRequest request, AppConfig config) {
        RespMessage rmsg = signChecking(request, config, TypeUtil.OPEN);
        return new Gson().toJson(rmsg);
    }


    /**
     * 应用关闭
     *
     * @param request
     */
    public String close(HttpServletRequest request, AppConfig config) {
        RespMessage rmsg = signChecking(request, config, TypeUtil.CLOSE);
        return new Gson().toJson(rmsg);
    }


    /**
     * 应用配置
     * 加载应用配置页
     *
     * @param request
     */
    public String config(HttpServletRequest request, AppConfig config) {
        RespMessage rmsg = signChecking(request, config, TypeUtil.CONFIG);
        return new Gson().toJson(rmsg);
    }

    /**
     * 应用监控
     * 返回ECHOSTR字段
     *
     * @param request
     */
    public String monitor(HttpServletRequest request, AppConfig config) {
        Map<String, String> parameters = (Map<String, String>) request.getAttribute(NameUtil.PARAMETERS);
        return parameters.get(NameUtil.ECHOSTR);
    }

    /**
     * 应用触发
     *
     * @param request
     */
    abstract String trigger(HttpServletRequest request, HttpServletResponse response, AppConfig
            config);

    /**
     * 修改关键字
     * 消息回复类支持模糊匹配的应用需提供此接口
     *
     * @param request
     */
    public String keyword(HttpServletRequest request, AppConfig config) {
        RespMessage rmsg = signChecking(request, config, TypeUtil.KEYWORD);
        return new Gson().toJson(rmsg);
    }

    /**
     * 其他type操作
     *
     * @param request
     * @param config
     * @return
     */
    abstract String other(HttpServletRequest request, AppConfig config);

    private RespMessage signChecking(HttpServletRequest request, AppConfig config,
                                     String type) {
        RespMessage rmsg = new RespMessage();
        Map<String, String> parameters = (Map<String, String>) request.getAttribute(NameUtil.PARAMETERS);
        if (!parameters.containsKey(NameUtil.SIGNATURE)) {
            rmsg.setCodeAndMsg(ErrorCodeUtil.PARAMETERS_WRONG, "parameters wrong");
        } else {
            String sign = parameters.get(NameUtil.SIGNATURE);
            parameters.remove(NameUtil.SIGNATURE);
            String calSign = SignUtil.getSinature(parameters, config.getApiSecret());
            logger.info("CalSign is :"+calSign + "; Send Sign is" + sign);
            if (calSign != null && sign != null && sign.equals(calSign)) {
                Long localTime = Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10));
                Long wxTime = Long.valueOf(parameters.get(NameUtil.TIMESTAMP));
                long interval = localTime - wxTime;
                //long interval = System.currentTimeMillis() / 1000 - Long.valueOf(parameters.get(NameUtil.TIMESTAMP));
                if ((interval >= 0 && interval < 10)||(interval <= 0 && interval >= -30)) {
                    String mediaId = parameters.get(NameUtil.MEDIAID);
                    switch (type) {
                        case TypeUtil.CLOSE:
                            closeOperation(mediaId, rmsg, config);
                            break;
                        case TypeUtil.OPEN:
                            openOperation(mediaId, rmsg, config);
                            break;
                        case TypeUtil.CONFIG:
                            configOperation(request, mediaId, rmsg, config);
                            break;
                        case TypeUtil.KEYWORD:
                            keywordOperation(request, rmsg, config);
                            break;
                    }

                } else {
                    logger.error("Request interface failed, Interval is not between 0 and 100. Interval : "+interval);
                    rmsg.setCodeAndMsg(ErrorCodeUtil.INTERFACE_FAILED, "request interface failed");
                }
            } else {
                logger.error("SIGN WRONG");
                rmsg.setCodeAndMsg(ErrorCodeUtil.SIGN_CHECK_FAILED, "sign wrong");
            }
        }
        return rmsg;
    }


    /**
     * 开启应用时进行的操作
     */
    abstract void openOperation(String mediaId, RespMessage rmsg, AppConfig config);

    /**
     * 关闭操作
     */
    abstract void closeOperation(String mediaId, RespMessage rmsg, AppConfig config);

    /**
     * 配置操作
     */
    abstract void configOperation(HttpServletRequest request, String mediaId, RespMessage rmsg,
                                  AppConfig
                                          config);

    abstract void keywordOperation(HttpServletRequest request, RespMessage rmsg, AppConfig config);
}
