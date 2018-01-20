package org.wx.weixiao.servlet;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.wx.weixiao.util.CommonUtil;
import org.wx.weixiao.util.NameUtil;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 2016/12/19.
 */
public class Util {
    private static Logger logger=Logger.getLogger(Util.class);
    /**
     * 预处理输入
     * 并将处理参数结果放入Attribute里
     * 很奇怪的一个事情的传入的json参数是作为key传入的，其对应的value为空
     *
     * @param request
     */
    public static void analyseParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        String jsonInput = "";
        StringBuilder logMess = new StringBuilder("Original Parameters:");
        for (Object obj : request.getParameterMap().keySet()) {
            logMess.append(obj).append("=").append(new Gson().toJson(request.getParameterMap().get(obj))).append("\n");
            if (!CommonUtil.isJson((String) obj)) {
                parameters.put((String) obj, ((String[]) request.getParameterMap().get(obj))[0]);
            } else {
                jsonInput = (String) obj;
            }
        }
        if (jsonInput.length() != 0) {
            Map<String, Object> temParameters = new Gson().fromJson(jsonInput, Map.class);
            for (String key : temParameters.keySet()) {
                String value = String.valueOf(temParameters.get(key));
                //防止科学计数法
                if (temParameters.get(key) instanceof Number) {
                    value = new BigDecimal(value).toPlainString();
                }
                parameters.put(key, value);
            }
        }
        parameters.remove("type");
        request.setAttribute(NameUtil.PARAMETERS, parameters);
        logMess.append("After Processing Parameters:").append(new Gson().toJson(parameters));

        logger.info(logMess);
    }
}
