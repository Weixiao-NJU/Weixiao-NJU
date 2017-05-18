package org.wx.weixiao.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by darxan on 2017/4/25.
 */
@Entity
public class Lecture implements FakeDeletable{
    /**
     * 回答id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer lid;

    private Integer id;

    private String title;

    private String teacher;

    private String academy;

    private Date startTime;

    private Date endTime;

    private String place;

    private Integer interestNum;

    @OneToMany(mappedBy = "lecture",cascade = CascadeType.ALL)
    private List<LectureSubscriber> subscriberList;

    @Lob
    @Type(type = "text")
    private String introduction;

    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getInterestNum() {
        return interestNum;
    }

    public void setInterestNum(Integer interestNum) {
        this.interestNum = interestNum;
    }

    public List<LectureSubscriber> getSubscriberList() {
        return subscriberList;
    }

    public void setSubscriberList(List<LectureSubscriber> subscriberList) {
        this.subscriberList = subscriberList;
    }

    /**
     * 是否被删除
     */
    @Basic
    @Column(columnDefinition = "INT default 0")
    private Integer isDelete;

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }


}
