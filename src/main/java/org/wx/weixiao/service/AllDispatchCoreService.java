package org.wx.weixiao.service;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Daniel on 2016/12/19.
 */
public interface AllDispatchCoreService {

    /**
     * 对请求分析并组合成{@link org.wx.weixiao.message.resp.BaseMessage}
     * 转化为xml格式返回String
     * @param request
     * @return response String
     */
    String handleRequest(HttpServletRequest request);

    /**
     * 对请求分析并组合成{@link org.wx.weixiao.message.resp.BaseMessage}
     * 转化为xml格式返回String,处理各种各样的全转发事务请求以及消息请求
     * @param parameters
     * @return response String
     */
    String handleInput(Map<String, String> parameters);
}
