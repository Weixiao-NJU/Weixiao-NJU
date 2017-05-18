package org.wx.weixiao.Info;

import org.wx.weixiao.dao.DAOManager;
import org.wx.weixiao.model.Lecture;

import java.util.Date;

/**
 * Created by darxan on 2017/4/26.
 */
public class LectureInfo {

    public LectureInfo() {
    }

    public LectureInfo(Lecture lecture) {
        if(lecture!=null) {
            lid = lecture.getLid();
            id = lecture.getId();
            title = lecture.getTitle();
            teacher = lecture.getTeacher();
            academy = lecture.getAcademy();
            startTime = lecture.getStartTime();
            endTime = lecture.getEndTime();
            place = lecture.getPlace();
            introduction = lecture.getIntroduction();
            interestNum = lecture.getInterestNum();
            subscriberNum= DAOManager.lectureSubscriberDao.getSubscriberNum(lid);
        }
    }

    public Lecture toLecture() {
        Lecture lecture = new Lecture();
        LectureInfo lectureInfo = this;
        lecture.setLid(lectureInfo.getLid());
        lecture.setId(lectureInfo.getId());
        lecture.setTitle(lectureInfo.getTitle());
        lecture.setTeacher(lectureInfo.getTeacher());
        lecture.setAcademy(lectureInfo.getAcademy());
        lecture.setStartTime(lectureInfo.getStartTime());
        lecture.setEndTime(lectureInfo.getEndTime());
        lecture.setPlace(lectureInfo.getPlace());
        lecture.setIntroduction(lectureInfo.getIntroduction());
        lecture.setInterestNum(lectureInfo.getInterestNum());
        return lecture;
    }

    private Integer lid;
    private Integer id;
    private String title;
    private String teacher;
    private String academy;
    private Date startTime;
    private Date endTime;
    private String place;
    private String introduction;
    private Integer interestNum;
    private Integer subscriberNum;


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

    @Override
    public String toString() {
        return "LectureInfo{" +
                "lid=" + lid +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", teacher='" + teacher + '\'' +
                ", academy='" + academy + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", place='" + place + '\'' +
                ", introduction='" + introduction + '\'' +
                ", interestNum=" + interestNum +
                '}';
    }

    public Integer getSubscriberNum() {
        return subscriberNum;
    }

    public void setSubscriberNum(Integer subscriberNum) {
        this.subscriberNum = subscriberNum;
    }
}
