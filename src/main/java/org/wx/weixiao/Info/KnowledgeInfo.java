package org.wx.weixiao.Info;

/**
 * Created by zs14 on 2016/12/24.
 */
public class KnowledgeInfo {
    private String id;
    private String question;
    private String answer;
    private String mediaId;
    private String heat;


    public KnowledgeInfo(){
    }

    public KnowledgeInfo(String id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public String getQuestion() {
        return question;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
