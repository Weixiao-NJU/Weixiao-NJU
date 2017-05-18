package org.wx.weixiao.util.weixiaoapi;

import com.google.gson.Gson;
import org.wx.weixiao.Info.UserInfo;
import org.wx.weixiao.util.HttpRequestUtil;

/**
 * Created by Daniel on 2017/2/21.
 */
public class UserInfoAPI {
    private static final String url = "https://api.weixin.qq" +
            ".com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    public static final UserInfo getUserInfo(String openId) {
        String token = AccessTokenGetter.getToken();
        return new Gson().fromJson(HttpRequestUtil.sendGet(String.format(url, token,
                openId)), UserInfo.class);
    }

    public static void main(String[]args){
        AccessTokenGetter.getToken();
        long a = System.currentTimeMillis();
        System.out.println(new Gson().toJson(getUserInfo("o25Awv4T2iYijsMwF8wQiuQ81ha4")));
        System.out.println(System.currentTimeMillis()-a);
    }
}
