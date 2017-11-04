package org.wx.weixiao.util.weixiaoapi;

import com.google.gson.Gson;
import org.wx.weixiao.message.weixiao.*;
import org.wx.weixiao.servlet.conf.AppConfig;
import org.wx.weixiao.util.HttpRequestUtil;
import org.wx.weixiao.util.SignUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by darxan on 2016/12/30.
 */
public class CustomMessageAPI {


    private static final String CUSTOM_MESSAGE_URL = "http://weixiao.qq.com/open/app_cgi/custom_message";


    /**
     * @param customMessage 必须设置好 can not be null.
     *                      {@link CustomMessage#media_id }
     *                      {@link CustomMessage#openid }
     *                      {@link CustomMessage#data }
     * @param appConfig     这个类在Servlet启动的时候new出来{@link org.wx.weixiao.servlet.AllDispatchServlet}
     * @return
     */
    public static CustomMessageResponse sendCustomMessage
    (CustomMessage customMessage, AppConfig appConfig) {
        customMessage.setApi_key(appConfig.getApiKey());
        customMessage.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
        encrypt(customMessage, appConfig);

        return sendMessage(customMessage);
    }


    protected static String encrypt(CustomMessage customMessage, AppConfig config) {

        Map<String, String> map = new HashMap<>(5);
        map.put("media_id", customMessage.getMedia_id());
        map.put("nonce_str", customMessage.getNonce_str());
        map.put("openid", customMessage.getOpenid());
        map.put("timestamp", customMessage.getTimestamp());
        map.put("api_key", customMessage.getApi_key());


        String sign = SignUtil.getSinature(map, config.getApiSecret());
        customMessage.setSign(sign);

        return sign;
    }


    protected static CustomMessageResponse sendMessage(CustomMessage message) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(message));
        String result = HttpRequestUtil.sendPostJSONByOKHttp3(CUSTOM_MESSAGE_URL, gson.toJson(message));
        CustomMessageResponse response = gson.fromJson(result, CustomMessageResponse.class);
        return response;
    }


    /**
     * =====================
     * 以下是测试代码 请忽略
     * =====================
     */

    public static CustomMessageResponse test() {

        AppConfig appConfig = new AppConfig();
        appConfig.setApiSecret("F22DC29E0BD53B0AF9E2CBA8F8E39EF6");
        appConfig.setApiKey("2B926F2C909A943E");

        CustomMessage customMessage = new CustomMessage();
        customMessage.setApi_key(appConfig.getApiKey());

        CustomMessageTextData customMessageTextData = new CustomMessageTextData();
        customMessageTextData.setContent("test content!!");

        CustomMessageNewsData customMessageNewsData = new CustomMessageNewsData();

        NewsContent newsContent = new NewsContent();
        newsContent.setTitle("您有未解决的学生提问，请尽早回复同学提问：）");
        newsContent.setDescription("ggggggggggggggggggggggggggggggg");
        newsContent.setImage("");
        newsContent.setUrl("zhouhangxx.3322.org/core/answer_question?question_id\\u003d9\\u0026answerer_id\\u003d1");

        customMessageNewsData.setNews(Arrays.asList(newsContent));

        customMessage.setData(customMessageNewsData);
        customMessage.setMedia_id("gh_41594420b805");
        customMessage.setOpenid("o25Awv8SDnc3mU1scv3Txz1UyQs4");
        customMessage.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));


        return sendCustomMessage(customMessage, appConfig);
    }

    public static void main(String[] args) {
        new Gson().toJson(test(), System.out);
    }
}
