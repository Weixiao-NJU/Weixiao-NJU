package org.wx.weixiao.dao;

import org.springframework.stereotype.Repository;
import org.wx.weixiao.dao.base.BaseDao;
import org.wx.weixiao.model.Questioner;

/**
 * Created by Daniel on 2016/12/19.
 */
@Repository
public class QuestionerDao extends BaseDao<Questioner,Integer> {

    public Questioner getQuestionerbyOpenId(String openId){
        String hqlString="from Questioner q where q.open_id=?";
        Questioner re =  getByHQL(hqlString,openId);
        if(re != null){
            return re;
        }else{
            return null;
        }
    }

}
