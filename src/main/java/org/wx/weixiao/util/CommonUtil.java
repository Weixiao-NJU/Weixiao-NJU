package org.wx.weixiao.util;


import com.google.gson.JsonParser;

/**
 * 通用工具类
 *
 * @author Aaron
 * @date 2014-6-23
 */
public class CommonUtil {


    /**
     * 获得长度为length的随机字符串
     *
     * @param length
     * @return
     */
    public static String randomString(int length) {
        if (length <= 0)
            return "";
        String base = "abcdefghijklmnopqrstuvwxzy0123456789";
        int size = base.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * size);
            result.append(base.charAt(index));
        }
        return result.toString();
    }

    public static boolean isJson(String json) {
        return new JsonParser().parse(json).isJsonObject();
    }
}