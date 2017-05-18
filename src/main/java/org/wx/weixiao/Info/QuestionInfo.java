package org.wx.weixiao.Info;

/**
 * Created by zs14 on 2016/12/15.
 */
public class QuestionInfo {
    private String id;
    private String content;
    private String createUser;
    private String createAt;
    private String questioner_open_id;
    private String keyword;
    private String question_media_id;
    private int isAnswered;

    public QuestionInfo(){
    }

    public QuestionInfo(String id, String content, String createUser, String createAt, String questioner_open_id, String keyword) {
        this.id = id;
        this.content = content;
        this.createUser = createUser;
        this.createAt = createAt;
        this.questioner_open_id = questioner_open_id;
        this.keyword = keyword;
    }

    public int getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(int isAnswered) {
        this.isAnswered = isAnswered;
    }

    public String getQuestion_media_id() {
        return question_media_id;
    }

    public void setQuestion_media_id(String question_media_id) {
        this.question_media_id = question_media_id;
    }

    public String getQuestioner_open_id() {
        return questioner_open_id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setQuestioner_open_id(String questioner_open_id) {
        this.questioner_open_id = questioner_open_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
