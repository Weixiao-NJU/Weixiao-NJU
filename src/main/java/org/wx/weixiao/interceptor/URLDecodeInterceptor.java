package org.wx.weixiao.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.wx.weixiao.util.AppConfigUtil;
import org.wx.weixiao.util.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * 对所有的URL进行解密处理
 * Created by Jerry Wang on 2017/1/4.
 */
public class URLDecodeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        try {
            String code = httpServletRequest.getParameter("encrypt").replace(' ','+');
            System.out.println("---------------------Analysis Data--------------------"+code);
            Map<String,String> parameters = SecurityUtil.decodeAsMap(code, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
            if(parameters == null)
                return false;
            for(Map.Entry<String,String> para : parameters.entrySet()){
                httpServletRequest.setAttribute(para.getKey(),para.getValue());
                System.out.println(para.getKey()+"  =========  "+para.getValue());
                //如果想修改需要重写HttpServletRequestWrapper
                //httpServletRequest.getParameterMap().put(para.getKey(),new String[]{para.getValue()});
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
