package org.wx.weixiao.model;

import javax.persistence.*;

/**
 * Created by darxan on 2017/4/26.
 */
@Entity
public class LectureSubscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer subscriber_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lid")
    private Lecture lecture;

    private String openid;

    private String email;

    private String phone;

    public Integer getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(Integer subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
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
}
