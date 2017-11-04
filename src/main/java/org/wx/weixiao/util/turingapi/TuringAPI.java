package org.wx.weixiao.util.turingapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.wx.weixiao.exception.turingrobot.RequestResultException;
import org.wx.weixiao.util.HttpRequestUtil;

/**
 * Created by Daniel on 2017/4/26.
 */
public class TuringAPI {
    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static Logger logger= Logger.getLogger(TuringAPI.class);

    public static JsonObject getQueryResult(TuringInput turingInput) throws RequestResultException{
        String result = HttpRequestUtil.sendPostJSONByOKHttp3(URL, new Gson().toJson(turingInput));

        try {
            logger.info("Turing Input : "+new Gson().toJson(turingInput)+" ; TURING RESULT : "+result);
            return new JsonParser().parse(result).getAsJsonObject();
        } catch (Exception e) {
            throw new RequestResultException();
        }
    }

    public static void main(String args[]) {
        TuringInput input = new TuringInput();
        input.setInfo("nihao");
        //input.setKey();
        System.out.println();
    }

}
