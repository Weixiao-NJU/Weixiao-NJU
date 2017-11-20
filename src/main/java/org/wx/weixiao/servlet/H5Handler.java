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
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Daniel on 2016/12/19.
 */
@Component("H5Handler")
public class H5Handler extends AbstractHandler {

    private static final String WEIXIAO_AUTH = "http://weixiao.qq.com/apps/school-auth/login?media_id=%s&app_key=%s&redirect_uri=%s";

    @Override
    String trigger(HttpServletRequest request, HttpServletResponse response, AppConfig config) {
        Map<String, String> parameters = (Map<String, String>) request.getAttribute(NameUtil.PARAMETERS);
        HttpSession session = request.getSession();
        try {
            if (parameters.get("wxcode") == null) {
                session.setAttribute("queryType", parameters.get("queryType"));
                String finalPage = "desktop.nju.edu.cn/h5?queryType="+parameters.get("queryType")+"&type=trigger";
                String redirectLink = String.format(WEIXIAO_AUTH, parameters.get(NameUtil
                        .MEDIAID), config.getApiKey(),URLEncoder.encode(finalPage));
                logger.info("The link is redirecting to "+redirectLink);
                response.sendRedirect(redirectLink);
            } else {
                String queryType = "grade";
                if (session.getAttribute("queryType") != null) {
                    queryType = (String) session.getAttribute("queryType");
                }
                String code = parameters.get("wxcode");
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

    public static void main(String args[]){
        System.out.println(URLEncoder.encode("http://219.219.120.44/h5?type=trigger&queryType=classroom&media_id=gh_41594420b805&nsukey=SIWBnignK7hj1P0UemgTp2WXY5x1Hs0VvPUHfdAUuD74Z4MmSQIa6iZHMhs3fysNIDkub%2F7%2FEx3kC1MJNS7briVXNIAxG5X307KGI72%2BQ5%2FIvJ7g4UsZkOPgCpy7veGR3FBRtGYvqUz7Yk99boZ%2FLYQ%2FUnqJbIkvZNQ3iYlbIKioIw5FFgkaxy9Wzse6rlwcD4izl6a39eEsjCjs%2BjddSw%3D%3D"));
    }

}
