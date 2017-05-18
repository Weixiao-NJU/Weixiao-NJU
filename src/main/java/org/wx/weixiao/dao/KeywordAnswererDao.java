package org.wx.weixiao.dao;

import org.springframework.stereotype.Repository;
import org.wx.weixiao.dao.base.BaseDao;
import org.wx.weixiao.model.Answerer;
import org.wx.weixiao.model.KeywordAnswer;

import java.util.List;

/**
 * Created by Daniel on 2016/12/27.
 */
@Repository
public class KeywordAnswererDao extends BaseDao<KeywordAnswer,Integer> {

    public Answerer getAnswerersbyKeyword(String keyword){
        String hql = "from KeywordAnswerer k where keyword=?";
        List<KeywordAnswer> keywordAnswers = this.getListByHQL(hql,keyword);
        return keywordAnswers.get(0).getAnswerer();

    }


    public KeywordAnswer getKeyWordBykeyword(String keyword){
        String hql = "select distinct k from KeywordAnswer k where k.keyword=?";
        return getByHQL(hql,keyword);
    }

    public List<KeywordAnswer> getKeyWordByAnswererId(int answererId){
        String hql = "from KeywordAnswer k where k.answerer.answId=?";
        return getListByHQL(hql,answererId);
    }

    public KeywordAnswer getKeyWordBykeyword_AnswererId(String keyword,int answererId){
        String hql = "select distinct k from KeywordAnswer k where k.keyword=? and k.answerer.answId=?";
        List<KeywordAnswer> re = getListByHQL(hql,keyword,answererId);
        if(re.isEmpty())
            return null;
        else
            return re.get(0);
    }

    public KeywordAnswer getDeletedKeyWord(String keyword,int answererId){
        String hql = "select distinct k from KeywordAnswer k where k.keyword=? and k.answerer.answId=? and k.isDelete=1";
        return getDeletedObjectbyHQL(hql,keyword,answererId);
    }
}
