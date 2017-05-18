package org.wx.weixiao.exception.turingrobot;

/**
 * Created by Daniel on 2017/4/26.
 */
public class UserNullException extends Exception {
    public UserNullException() {
        super("用户id为空");
    }
}
