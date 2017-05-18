package org.wx.weixiao.message.weixiao;

/**
 * Created by darxan on 2016/12/30.
 */
public class CustomMessageTextData extends CustomMessageBaseData {

    protected String content;

    public CustomMessageTextData() {
        super("text");
        content = "";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
