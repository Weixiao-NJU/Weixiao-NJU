package org.wx.weixiao.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wx.weixiao.Info.LectureInfo;
import org.wx.weixiao.common.LoggerManager;
import org.wx.weixiao.dao.LectureDao;
import org.wx.weixiao.dao.LectureSubscriberDao;
import org.wx.weixiao.model.Lecture;
import org.wx.weixiao.service.LectureTimerService;
import org.wx.weixiao.util.EmailUtil;
import org.wx.weixiao.util.LectureUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by darxan on 2017/4/26.
 */
@Component("LectureTimerService")
public class LectureTimerImpl implements LectureTimerService {

    @Autowired
    private LectureDao lectureDao;

    @Autowired
    private LectureSubscriberDao lectureSubscriberDao;

    private static Logger logger=Logger.getLogger(LectureTimerImpl.class);

    public void updateLectures() {
        int lastLectureId = lectureDao.getNewestLectureId();
        LoggerManager.info(logger, "One iteration for update lectures, add from: "+lastLectureId);
        List<LectureInfo> lectureInfos = LectureUtil.read(null, lastLectureId);
        //saveOrUpdate
        for (LectureInfo lectureInfo:lectureInfos) {
            Lecture lecture = lectureInfo.toLecture();
            lecture.setInterestNum(0);
            lecture.setIsDelete(0);
            lectureDao.save(lecture);
            LoggerManager.info(logger, "Add one lecture"+lecture.getTitle());
        }
    }

    public void emailSubscribers(long before) {
        List<Lecture> emailedLectures =
                lectureDao.getShouldEmailsLecture(new Date(), new Date(System.currentTimeMillis()+before));
        System.out.println(emailedLectures.size());
        emailedLectures.forEach(
                lecture -> EmailUtil.email(lecture) );
    }

    public void textSubscribers() {

    }

}
