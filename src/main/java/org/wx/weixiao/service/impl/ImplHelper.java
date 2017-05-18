package org.wx.weixiao.service.impl;

import com.google.gson.Gson;
import org.wx.weixiao.util.AppConfigUtil;
import org.wx.weixiao.util.SecurityUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jerry Wang on 2017/1/4.
 * 辅助类 各种杂活
 */
public class ImplHelper {

    private ImplHelper(){}

    public static String generateCodebyMid_Aid(String media_id,String answerer_id){
        Map<String,String> para = new HashMap<>();
        para.put("media_id",media_id);
        para.put("answerer_id",answerer_id);
        return SecurityUtil.encode(para, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
    }

    public static String generateCodebyQuestionId(String question_id){
        Map<String,String> para = new HashMap<>();
        para.put("question_id",question_id);
        return SecurityUtil.encode(para, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
    }

    public static String generateCodebyAnswererId(String answererId){
        Map<String,String> para = new HashMap<>();
        para.put("answerer_id",answererId);
        return SecurityUtil.encode(para, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
    }

    public static String generateCodebyOpenId(String openId){
        Map<String,String> para = new HashMap<>();
        para.put("open_id",openId);
        return SecurityUtil.encode(para, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
    }

    public static String generateCodebyMediaId(String mediaId){
        Map<String,String> para = new HashMap<>();
        para.put("media_id",mediaId);
        return SecurityUtil.encode(para, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
    }

    public static String generateCode(String media_id,String open_id){
        Map<String,String> para = new HashMap<>();
        para.put("open_id",open_id);
        para.put("media_id",media_id);
        return SecurityUtil.encode(para, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
    }

    public static String generateCodebyQid_Aid(String question_id,String answerer_id){
        Map<String,String> para = new HashMap<>();
        para.put("question_id",question_id);
        para.put("answerer_id",answerer_id);
        para.put("flag","0");
        System.out.println(new Gson().toJson(para));
        return SecurityUtil.encode(para, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
    }
}
