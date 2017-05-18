package org.wx.weixiao.model;

import org.wx.weixiao.dao.DAOManager;

import javax.persistence.*;

/**
 * Created by lizhimu on 2017/4/30.
 */
@Entity
public class LectureInterester {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer interest_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lid")
    private Lecture lecture;

    private String openid;

    public LectureInterester(String openId, int lid) {
        this.openid=openId;
        this.lecture= DAOManager.lectureDao.getLectureByLId(lid);
    }

    public LectureInterester() {
    }


    public Integer getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(Integer interest_id) {
        this.interest_id = interest_id;
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


}
