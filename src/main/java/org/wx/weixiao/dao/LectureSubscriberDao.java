package org.wx.weixiao.dao;

import org.springframework.stereotype.Repository;
import org.wx.weixiao.dao.base.BaseDao;
import org.wx.weixiao.model.LectureSubscriber;

import java.util.List;

/**
 * Created by lizhimu on 2017/4/26.
 */
@Repository
public class LectureSubscriberDao extends BaseDao<LectureSubscriber,Integer> {
    public void saveLectureSubscriberInfo(LectureSubscriber applicant) {
        save(applicant);
    }

    public List<LectureSubscriber> getLectureSubscribers(int lid) {
        return getListByHQL("from LectureSubscriber  c where c.lecture.lid=?",lid);
    }

    public List<LectureSubscriber> getLectureSubscribersByOpenId(String openid) {
        return getListByHQL("from LectureSubscriber  c where c.openid=?",openid);
    }

    public int getSubscriberNum(int lid){
        return getLectureSubscribers(lid).size();
    }

    public boolean getIsSubscriber(String openId, int lid) {
        List<LectureSubscriber> subScriberList=getLectureSubscribers(lid);
        for(LectureSubscriber l:subScriberList){
            if(l.getOpenid().equals(openId)){
                return true;
            }
        }
        return false;
    }
}
