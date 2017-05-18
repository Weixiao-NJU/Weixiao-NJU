package org.wx.weixiao.service.vo;

/**
 * Created by darxan on 2016/12/29.
 */
public class ReAjaxResponse {

    private boolean success = true;

    private String message = "ok";

    private Object content = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
