package org.wx.weixiao.Info;

/**
 * Created by zs14 on 2016/12/28.
 */
public class AnswerInfo {
    //对应问题Id
    private String questionId;
    //对应答案
    private String content;
    //对应回答者的openId-提交的时候不为空，返回的时候可为空
    private String answererId;
    //对应回答者的名字-提交的时候为空，返回的时候不能为空
    private String answererName;
    //回答时间
    private String answerTime;
    //是否公开
    private boolean isOpen;

    public AnswerInfo() {
    }
    public AnswerInfo(String questionId, String content, String answererId, String answererName, String answerTime) {
        this.questionId = questionId;
        this.content = content;
        this.answererId = answererId;
        this.answererName = answererName;
        this.answerTime=answerTime;
    }

    public AnswerInfo(String questionId, String content, String answererId, String answererName, String answerTime, boolean isOpen) {
        this.questionId = questionId;
        this.content = content;
        this.answererId = answererId;
        this.answererName = answererName;
        this.isOpen = isOpen;
        this.answerTime=answerTime;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswererId() {
        return answererId;
    }

    public void setAnswererId(String answererId) {
        this.answererId = answererId;
    }

    public String getAnswererName() {
        return answererName;
    }

    public void setAnswererName(String answererName) {
        this.answererName = answererName;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    @Override
    public String toString() {
        return "AnswerInfo{" +
                "questionId='" + questionId + '\'' +
                ", content='" + content + '\'' +
                ", answererId='" + answererId + '\'' +
                ", answererName='" + answererName + '\'' +
                ", answerTime='" + answerTime + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }
}
