package org.wx.weixiao.model;


import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Daniel on 2016/12/29.
 */
@Entity
public class Answer implements FakeDeletable {
    /**
     * 回答id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer aid;

    /**
     * 回答者
     */
    @ManyToOne
    @JoinColumn(name = "answId")
    private Answerer answerer;
    /**
     * 回答时间
     */
    private Timestamp createAt;
    /**
     * 是否被删除
     */
    @Basic
    @Column(columnDefinition = "INT default 0")
    private Integer isDelete;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "questionId")
    private Question question;

    /**
     * 回答内容
     */
    private String content;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Answerer getAnswerer() {
        return answerer;
    }

    public void setAnswerer(Answerer answerer) {
        this.answerer = answerer;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
