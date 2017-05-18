package org.wx.weixiao.util.weixiaoapi;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.wx.weixiao.util.HttpRequestUtil;

/**
 * Created by Daniel on 2017/2/21.
 */
public class AccessTokenGetter {
    private static String token = null;
    private static long timeMillis = -1;
    private static String appid = "wx447ce4c66c6504f4";
    private static String appSecret = "5a3fc5377faf2d36010d2b59b5f1e5fd";

    private static final String url = "https://api.weixin.qq" +
            ".com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";


    /**
     * 获得token，根据之前的timeMillis判断是否需要刷新
     *
     * @return
     */
    public static String getToken() {
        if (!needToFlush())
            return token;
        return getTokenNow();
    }

    private static String getTokenNow() {

        JsonElement jsonObject = new JsonParser().parse(HttpRequestUtil.sendGet(String.format(url, appid,
                appSecret)));
        token = jsonObject.getAsJsonObject().get("access_token").getAsString();
        timeMillis = System.currentTimeMillis();
        return token;
    }

    private static boolean needToFlush() {
        if (timeMillis + 1000 * 7200 < System.currentTimeMillis())
            return true;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(getToken());
        System.out.println(token);
    }

}
