package org.wx.weixiao.message.weixiao;


import org.wx.weixiao.util.CommonUtil;

/**
 * Created by darxan on 2016/12/30.
 */
public class CustomMessage {

    public CustomMessage() {
        nonce_str = CommonUtil.randomString(32);
    }
    /**
     * 公众号原始id
     */
    private String media_id;
    /**
     * 应用key
     */
    private String api_key;
    /**
     * 用户的openid
     */
    private String openid;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 32 位随机字符串
     */
    private String nonce_str;
    /**
     *
     */
    private CustomMessageBaseData data;
    /**
     * 按照签名算法生成的数据签名
     */
    private String sign;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public CustomMessageBaseData getData() {
        return data;
    }

    public void setData(CustomMessageBaseData data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
