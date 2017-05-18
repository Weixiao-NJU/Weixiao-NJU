package org.wx.weixiao.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Daniel on 2016/12/19.
 */
@Entity
public class Answerer implements FakeDeletable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer answId;
    /**
     * 用户微信openId
     */
    private String openId;
    /**
     * 用户绑定身份密匙
     */
    private String secretWord;
    /**
     * 公众号id
     */
    @ManyToOne
    @JoinColumn(name = "mediaId")
    private MediaInfo mediaInfo;
    /**
     * 名字
     */
    private String name;
    /**
     * 电话号码
     */
    private String phoneNum;
    /**
     * 部门
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deptId")
    private Department department;

    @OneToMany(mappedBy = "answerer",  fetch = FetchType.EAGER)
    private List<KeywordAnswer> keywordAnswer;

    @OneToMany( mappedBy = "answerer",cascade = CascadeType.ALL)
    private List<Answer> answers;

    /**
     * 是否被删除
     */
    @Column(columnDefinition = "INT default 0")
    private Integer isDelete;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public Integer getIsDelete() {
        return isDelete;
    }

    @Override
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getAnswId() {
        return answId;
    }

    public void setAnswId(Integer answId) {
        this.answId = answId;
    }

    public MediaInfo getMediaInfo() {
        return mediaInfo;
    }

    public void setMediaInfo(MediaInfo mediaInfo) {
        this.mediaInfo = mediaInfo;
    }

    public List<KeywordAnswer> getKeywordAnswer() {
        return keywordAnswer;
    }

    public void setKeywordAnswer(List<KeywordAnswer> keywordAnswer) {
        this.keywordAnswer = keywordAnswer;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
