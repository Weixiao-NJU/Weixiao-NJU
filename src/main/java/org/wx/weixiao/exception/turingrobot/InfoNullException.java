package org.wx.weixiao.exception.turingrobot;

/**
 * Created by Daniel on 2017/4/26.
 * 输入信息为空
 */
public class InfoNullException extends Exception {
    public InfoNullException() {
        super("输入信息为空");
    }
}
