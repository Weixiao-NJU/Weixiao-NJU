package org.wx.weixiao.dao;

import org.springframework.stereotype.Repository;
import org.wx.weixiao.dao.base.BaseDao;
import org.wx.weixiao.model.Answer;

import java.util.List;

/**
 * Created by Jerry Wang on 2017/1/1.
 */
@Repository
public class AnswerDao extends BaseDao<Answer,Integer> {

    public List<Answer> getAnswersByQuesionId(String questionId){
        String hql = "from Answer where questionId=?";
        return getListByHQL(hql,questionId);
    }

}
