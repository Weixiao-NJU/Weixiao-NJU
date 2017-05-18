package org.wx.weixiao.Info;

import org.wx.weixiao.dao.DAOManager;
import org.wx.weixiao.model.LectureSubscriber;

/**
 * Created by darxan on 2017/4/26.
 */
public class LectureSubscriberInfo {

    private Integer subscriber_id;

    private Integer lectureLId;

    private String openid;

    private String email;

    private String phone;

    public LectureSubscriberInfo(){

    }

    public LectureSubscriberInfo(LectureSubscriber l){
        this.subscriber_id=l.getSubscriber_id();
        this.lectureLId=l.getLecture().getLid();
        this.openid=l.getOpenid();
        this.email=l.getEmail();
        this.phone=l.getPhone();
    }

    public LectureSubscriber toLectureSubscriber(){
        LectureSubscriber l=new LectureSubscriber();
        l.setSubscriber_id(this.getSubscriber_id());
        l.setEmail(this.getEmail());
        l.setPhone(this.getPhone());
        l.setLecture(DAOManager.lectureDao.getLectureByLId(this.getLectureLId()));
        l.setOpenid(this.getOpenid());

        return l;
    }

    public Integer getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(Integer subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public Integer getLectureLId() {
        return lectureLId;
    }

    public void setLectureLId(Integer lectureId) {
        this.lectureLId = lectureId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "LectureSubscriberInfo{" +
                "subscriber_id=" + subscriber_id +
                ", lectureId=" + lectureLId +
                ", openid=" + openid +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
