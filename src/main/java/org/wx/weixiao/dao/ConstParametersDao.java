package org.wx.weixiao.dao;

import org.springframework.stereotype.Repository;
import org.wx.weixiao.dao.base.BaseDao;
import org.wx.weixiao.model.ConstParameters;

/**
 * Created by Daniel on 2017/1/7.
 */
@Repository
public class ConstParametersDao extends BaseDao<ConstParameters, Integer> {
//    public void setStartTime(String startTime){
//        queryHql("update constparameters t set t.work_time_start = '"+startTime+"' where id = 1");
//    }
//
//    public void setEndTime(String endTime){
//        queryHql("update constparameters t set t.work_time_end = '"+endTime+"' where id = 1");
//    }


    public ConstParameters readParameters(String mediaId) {
        return getByHQL("from ConstParameters  c where c.mediaInfo.mediaId=?", mediaId);
    }

    public String readStartTime(String mediaId) {
        return readParameters(mediaId).getWork_time_start();
    }

    public String readEndTime(String mediaId) {
        return readParameters(mediaId).getWork_time_end();
    }

}

