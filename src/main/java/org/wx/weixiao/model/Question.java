package org.wx.weixiao.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Daniel on 2016/12/19.
 */
@Entity
public class Question implements FakeDeletable {
    /**
     * 问题id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questionId;
    /**
     * 提问时间
     */
    private Timestamp createAt;
    /**
     * 是否被删除
     */
    @Column(columnDefinition = "INT default 0")
    private Integer isDelete;
    /**
     * 问题是否开放
     */
    private Integer isOpen;

    /**
     * 问题关键字
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "keywordId")
    private KeywordAnswer keyword;
    /**
     * 问题内容
     */
    private String content;

    /**
     * 问题是否已推送
     */
    @Column(columnDefinition = "INT default 0")
    private boolean isDispatched;

    /**
     * 问题是否已经被回答
     */
    private Integer isAnswered;
    /**
     * 答案
     */
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Answer> answers;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mediaId")
    private MediaInfo mediaInfo;
    /**
     * 提问者
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quesId")
    private Questioner questioner;


    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public MediaInfo getMediaInfo() {
        return mediaInfo;
    }

    public void setMediaInfo(MediaInfo mediaInfo) {
        this.mediaInfo = mediaInfo;
    }

    public Questioner getQuestioner() {
        return questioner;
    }

    public KeywordAnswer getKeyword() {
        return keyword;
    }

    public void setKeyword(KeywordAnswer keyword) {
        this.keyword = keyword;
    }

    public void setQuestioner(Questioner questioner) {
        this.questioner = questioner;
    }

    public boolean isDispatched() {
        return isDispatched;
    }

    public void setDispatched(boolean dispatched) {
        isDispatched = dispatched;
    }

    public Integer getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(Integer isAnswered) {
        this.isAnswered = isAnswered;
    }
}
