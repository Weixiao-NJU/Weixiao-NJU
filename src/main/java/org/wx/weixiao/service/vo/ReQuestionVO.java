package org.wx.weixiao.service.vo;

/**
 * Created by Jerry Wang on 2016/12/20.
 */
public class ReQuestionVO {

    /**
     * 问题标题
     */
    public String question ;
    /**
     * 问题答案所对应的URL
     */
    public String questionurl;
    /**
     * 问题对应图片的URL
     */
    public String questionImgUrl;

    public ReQuestionVO(String question, String questionurl, String questionImgUrl) {
        this.question = question;
        this.questionurl = questionurl;
        this.questionImgUrl = questionImgUrl;
    }
}
