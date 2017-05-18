package org.wx.weixiao.message.weixiao;

/**
 * Created by darxan on 2016/12/30.
 */
public class CustomMessageResponse {

    private int errcode;

    private String errmsg;

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
}
