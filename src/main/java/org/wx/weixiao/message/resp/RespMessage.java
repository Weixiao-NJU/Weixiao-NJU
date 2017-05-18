package org.wx.weixiao.message.resp;

import java.util.Map;

/**
 * Created by Daniel on 2016/12/6.
 */
public class RespMessage {
    /**
     * 返回码  在开启应用时非空
     * 0表示成功
     */
    private Integer errcode;

    /**
     * 出错提示信息 在开启应用时可空
     */
    private String errmsg;
    /**
     * 应用触发地址token令牌 在开启应用时可空
     */
    private String token;
    /**
     * 是否有配置页 在开启应用时非空
     * 0: 无配置页; 1: 有配置页
     */
    private Integer is_config;
    /**
     * 自定义信息 在开启应用时可空
     * 格式为数组键值对（参考Custom常用参数说明）
     */
    private Map<String, String> custom;

    public void putCustom(String key, String value) {
        this.custom.put(key, value);
    }

    public void setCodeAndMsg(int errcode, String msg) {
        this.errcode = errcode;
        this.errmsg = msg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIs_config() {
        return is_config;
    }

    public void setIs_config(int is_config) {
        this.is_config = is_config;
    }

    public Map<String, String> getCustom() {
        return custom;
    }

    public void setCustom(Map<String, String> custom) {
        this.custom = custom;
    }

}
