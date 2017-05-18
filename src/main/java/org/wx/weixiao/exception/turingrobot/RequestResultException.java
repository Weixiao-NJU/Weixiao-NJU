package org.wx.weixiao.exception.turingrobot;

/**
 * Created by Daniel on 2017/4/26.
 */
public class RequestResultException extends Exception {
    public RequestResultException() {
        super("获取图灵api信息失败或格式错误");
    }
}
