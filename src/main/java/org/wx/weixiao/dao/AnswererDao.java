package org.wx.weixiao.dao;

import org.springframework.stereotype.Repository;
import org.wx.weixiao.dao.base.BaseDao;
import org.wx.weixiao.model.Answerer;

import java.util.List;

/**
 * Created by Daniel on 2016/12/19.
 */
@Repository
public class AnswererDao extends BaseDao<Answerer,Integer> {
    public List<Answerer> getListByColumnValue(String colName,Object colValue){
        String hqlString="from Answerer q where q."+colName+"=?";
        return getListByHQL(hqlString,colValue);
    }

    public Answerer getAnswererBySecretWord(String secretID){
        List<Answerer>list=getListByColumnValue("secretWord",secretID);
        if(!list.isEmpty()){
            return list.get(0);
        }else{
            return null;
        }
    }

    public Answerer getAnswererByOpenId(String openID){
        String hql = "from Answerer q where q.openId=?";
        return getByHQL(hql,openID);
    }

    public Answerer getAnswererByOpenId_MediaId(String openID,String mediaID){
        String hql = "from Answerer q where q.openId=? and q.mediaInfo.mediaId=?";
        return getByHQL(hql,openID,mediaID);
    }


    public List<Answerer> getAnswerersByMediaID(int mediaId){
        return getListByColumnValue("mediaInfo.mid",mediaId);
    }



}
