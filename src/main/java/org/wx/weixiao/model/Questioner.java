package org.wx.weixiao.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Daniel on 2016/12/19.
 */
@Entity
@Table(name = "questioner")
public class Questioner implements FakeDeletable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 用户OPEN_ID
     */
    private String open_id;
    /**
     * 用户在微校的唯一id
     */
    private String weixiao_id;
    /**
     * 校园账号
     */
    private String card_num;
    /**
     * 学生姓名
     */
    private String name;
    /**
     * 学生年级
     */
    private String grade;
    /**
     * 学生学院
     */
    private String college;
    /**
     * 学生专业
     */
    private String profession;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mediaId")
    private MediaInfo mediaInfo;

    @Column(columnDefinition = "INT default 0")
    private Integer isDelete;

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    @OneToMany(mappedBy = "questioner",cascade = CascadeType.ALL)
    private Set<Question> questions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeixiao_id() {
        return weixiao_id;
    }

    public void setWeixiao_id(String weixiao_id) {
        this.weixiao_id = weixiao_id;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public MediaInfo getMediaInfo() {
        return mediaInfo;
    }

    public void setMediaInfo(MediaInfo mediaInfo) {
        this.mediaInfo = mediaInfo;
    }
}
