package org.wx.weixiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wx.weixiao.appConnection.AppConnection;
import org.wx.weixiao.appConnection.ConnectionInfo;
import org.wx.weixiao.appConnection.InquiryUrl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zs14 on 2016/12/24.
 */
@Controller
public class InquiryController {

    //具体的查询内容
    @RequestMapping(value = "/inquiry")
    public void inquiry(@RequestParam("type") String type, HttpServletRequest request, HttpServletResponse response) {
        //获取用户信息，生成参数
//        String id=request.getParameter("id");
//        String userName=request.getParameter("userName");
//        ConnectionInfo info=new ConnectionInfo(id,userName);
//        AppConnection con=new AppConnection();
//        String paras=con.createConnection( info);
//        //根据type判断属于哪一种查询
//        System.out.println(type);
//        String url="";
//        try {
//            url=InquiryUrl.valueOf(type.toUpperCase()).getUrl();
//            String s=url+"?"+paras;
//            System.out.println(s);
//            response.sendRedirect(s);
//        }catch(IllegalArgumentException e) {
//            e.printStackTrace();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
