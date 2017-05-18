package org.wx.weixiao.dao;

import org.springframework.stereotype.Repository;
import org.wx.weixiao.dao.base.BaseDao;
import org.wx.weixiao.model.LectureInterester;

import java.util.List;

/**
 * Created by lizhimu on 2017/4/30.
 */
@Repository
public class LectureInteresterDao  extends BaseDao<LectureInterester,Integer> {

    public List<LectureInterester> getLectureInterestersByLid(int lid) {
        return getListByHQL("from LectureInterester  c where c.lecture.lid=?",lid);
    }

    public List<LectureInterester> getLectureInterestersByOpenId(String openid) {
        return getListByHQL("from LectureInterester  c where c.openid=?",openid);
    }
    public boolean getisInterest(String openId, int lid) {
        List<LectureInterester> interesterList=getLectureInterestersByLid(lid);
        for(LectureInterester l:interesterList){
            if(l.getOpenid().equals(openId)){
                return true;
            }
        }
        return false;
    }

    public void saveLectureInterester(String openId, int lid) {
        LectureInterester interester=new LectureInterester(openId,lid);
        save(interester);
    }
}
