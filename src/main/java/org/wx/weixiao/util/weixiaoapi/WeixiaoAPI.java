package org.wx.weixiao.util.weixiaoapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.wx.weixiao.Info.StudentInfo;
import org.wx.weixiao.model.MediaInfo;
import org.wx.weixiao.servlet.conf.AppConfig;
import org.wx.weixiao.util.CommonUtil;
import org.wx.weixiao.util.HttpRequestUtil;
import org.wx.weixiao.util.SignUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 2016/12/28.
 */
public class WeixiaoAPI {
    private static final String USER_INFO_URL = "http://weixiao.qq.com/open/identity/user_info";
    private static final String MEDIA_INFO_URL = "http://weixiao.qq.com/common/get_media_info";
    private static Logger logger= Logger.getLogger(WeixiaoAPI.class);


    public static StudentInfo getStudentInfo(String code, AppConfig appConfig) {
        Map<String, String> sendPara = new HashMap<>();
        sendPara.put("app_key", appConfig.getApiKey());
        sendPara.put("code", code);
        sendPara.put("nonce_str", CommonUtil.randomString(32));
        sendPara.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        String sign = SignUtil.getSinature(sendPara, appConfig.getApiSecret());
        sendPara.put("sign", sign);
        String result = HttpRequestUtil.sendPost(USER_INFO_URL, new Gson().toJson(sendPara));
        StudentInfo studentInfo = new Gson().fromJson(result, StudentInfo.class);
        return studentInfo;
    }


    public static MediaInfo getMediaInfo(String media_id, AppConfig appConfig) {
        Map<String, String> sendPara = new HashMap<>();
        sendPara.put("api_key", appConfig.getApiKey());
        sendPara.put("media_id", media_id);
        sendPara.put("nonce_str", CommonUtil.randomString(32));
        sendPara.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        String sign = SignUtil.getSinature(sendPara, appConfig.getApiSecret());
        sendPara.put("sign", sign);
        String paralog = new Gson().toJson(sendPara);
        String result = HttpRequestUtil.sendPost(MEDIA_INFO_URL, new Gson().toJson(sendPara));
        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        logger.info("ParaLog : "+paralog+"; Result Msg: "+jsonObject.toString());
        MediaInfo mediaInfo = new MediaInfo();
        mediaInfo.setIsDelete(0);
        mediaInfo.setMediaId(media_id);
        if (jsonObject.get("avatar_image") != null && !jsonObject.get("avatar_image").isJsonNull())
            mediaInfo.setAvatarImage(jsonObject.get("avatar_image").getAsString());
        mediaInfo.setMediaName(jsonObject.get("name").getAsString());
        if (jsonObject.get("media_num") != null && !jsonObject.get("media_num").isJsonNull())
            mediaInfo.setMediaNum(jsonObject.get("media_num").getAsString());
        mediaInfo.setMediaType(jsonObject.get("media_type").getAsInt());
        mediaInfo.setVerifyType(jsonObject.get("verify_type").getAsInt());
        mediaInfo.setSchoolName(jsonObject.get("school_name").getAsString());
        mediaInfo.setSchoolCode(jsonObject.get("school_code").getAsString());
        mediaInfo.setMediaUrl(jsonObject.get("media_url").getAsString());
        return mediaInfo;
    }


    public static void main(String[] args) {
        String code = "c51865390773cbcaed793dc9d356fd93";
        AppConfig appConfig = new AppConfig();
        appConfig.setApiSecret("E4C75B2EED6AA1C8287686C58E62E2C2");
        appConfig.setApiKey("FCFD4BB4F35E0770");
        StudentInfo studentInfo = getStudentInfo(code, appConfig);
        System.out.println(new Gson().toJson(studentInfo));

        String media_id = "gh_41594420b805";

        MediaInfo mediaInfo = getMediaInfo(media_id, appConfig);
        System.out.println(new Gson().toJson(mediaInfo));
    }
}
