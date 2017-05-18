package org.wx.weixiao.dao;

import org.springframework.stereotype.Repository;
import org.wx.weixiao.dao.base.BaseDao;
import org.wx.weixiao.model.KnowledgeBase;

import java.util.List;

/**
 * Created by Jerry Wang on 2016/12/17.
 */

@Repository
public class KnowledgeBaseDao extends BaseDao<KnowledgeBase,Integer>{

    public List<KnowledgeBase> getAllKnowledgeBase(String media_id){
        String hql = "from KnowledgeBase where mediaInfo.mediaId=?";
        return getListByHQL(hql,media_id);
    }

    public List<KnowledgeBase> getHeatKnowledgeBase(String media_id){
        String hql = "from KnowledgeBase k where k.mediaInfo.mediaId=? order by k.heat desc";
        return getListByHQL(hql,media_id);
    }

}
