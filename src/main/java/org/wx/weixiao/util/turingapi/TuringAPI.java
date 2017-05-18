package org.wx.weixiao.util.turingapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.wx.weixiao.exception.turingrobot.RequestResultException;
import org.wx.weixiao.util.HttpRequestUtil;

/**
 * Created by Daniel on 2017/4/26.
 */
public class TuringAPI {
    private static final String URL = "http://www.tuling123.com/openapi/api";

    public static JsonObject getQueryResult(TuringInput turingInput) throws RequestResultException{
        String result = HttpRequestUtil.sendJsonPost(URL, new Gson().toJson(turingInput));
        try {
            return new JsonParser().parse(result).getAsJsonObject();
        } catch (Exception e) {
            throw new RequestResultException();
        }
    }


}
