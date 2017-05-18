package org.wx.weixiao.servlet;

import org.springframework.stereotype.Component;
import org.wx.weixiao.Info.StudentInfo;
import org.wx.weixiao.appConnection.AppConnection;
import org.wx.weixiao.appConnection.ConnectionInfo;
import org.wx.weixiao.appConnection.InquiryUrl;
import org.wx.weixiao.message.resp.RespMessage;
import org.wx.weixiao.servlet.conf.AppConfig;
import org.wx.weixiao.util.ErrorCodeUtil;
import org.wx.weixiao.util.NameUtil;
import org.wx.weixiao.util.weixiaoapi.WeixiaoAPI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Daniel on 2016/12/19.
 */
@Component("H5Handler")
public class H5Handler extends AbstractHandler {

    @Override
    String trigger(HttpServletRequest request, HttpServletResponse response, AppConfig config) {
        Map<String, String> parameters = (Map<String, String>) request.getAttribute(NameUtil.PARAMETERS);
        HttpSession session = request.getSession();
        try {
            if (parameters.get("code") == null) {
                session.setAttribute("queryType", parameters.get("queryType"));
                response.sendRedirect(String.format("http://weixiao.qq" +
                        ".com/open/identity/login?media_id=%s&app_key=%s", parameters.get(NameUtil
                        .MEDIAID), config.getApiKey()));
            } else {
                String queryType = "grade";
                if (session.getAttribute("queryType") != null) {
                    queryType = (String) session.getAttribute("queryType");
                }
                String code = parameters.get("code");
                StudentInfo studentInfo = WeixiaoAPI.getStudentInfo(code, config);
                ConnectionInfo info=new ConnectionInfo(studentInfo.getCard_num(),studentInfo.getName());
                AppConnection con=new AppConnection();
                String paras=con.createConnection( info);
                //根据type判断属于哪一种查询
                String url="";
                try {
                    url= InquiryUrl.valueOf(queryType.toUpperCase()).getUrl();
                    String s=url+"?"+paras;
                    System.out.println(s);
                    response.sendRedirect(s);
                }catch(IllegalArgumentException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "trigger";
    }

    @Override
    String other(HttpServletRequest request, AppConfig config) {
        return "other";
    }

    @Override
    void openOperation(String mediaId, RespMessage rmsg, AppConfig config) {
        rmsg.setErrcode(ErrorCodeUtil.SUCCESS);
        rmsg.setErrmsg("ok");
        rmsg.setToken(config.getToken());
        rmsg.setIs_config(0);
    }

    @Override
    void closeOperation(String mediaId, RespMessage rmsg, AppConfig config) {
        rmsg.setCodeAndMsg(ErrorCodeUtil.SUCCESS, "ok");
    }

    @Override
    void configOperation(HttpServletRequest request, String mediaId, RespMessage rmsg, AppConfig config) {
        rmsg.setCodeAndMsg(ErrorCodeUtil.SUCCESS, "ok");
    }

    @Override
    void keywordOperation(HttpServletRequest request, RespMessage rmsg, AppConfig config) {
        rmsg.setCodeAndMsg(ErrorCodeUtil.SUCCESS, "ok");
    }
}
