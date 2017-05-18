package org.wx.weixiao.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.wx.weixiao.dao.base.BaseDao;
import org.wx.weixiao.model.Question;

import java.util.List;

/**
 * Created by Daniel on 2016/12/19.
 */
@Repository
public class QuestionDao extends BaseDao<Question, Integer> {

    public List<Question> getRecentQuestionByMediaId(String mediaId, int maxResult) {

        String hqlString ="from Question q WHERE q.mediaInfo.mediaId=? AND q.isOpen=1 ORDER BY q.createAt DESC";

        Query query = this.getSession().createQuery(hqlString);
        query.setParameter(0, mediaId);
        query.setMaxResults(maxResult);
        return query.list();
    }

    public List<Question> getUnsolvedQuestionByMediaId(String mediaId){
        String hqlString = "from Question q where q.isAnswered=0 and q.mediaInfo.mediaId=?";
        return getListByHQL(hqlString,mediaId);
    }

    public List<Question> getByAnswers(String answererId) {
        String sqlString = "SELECT Question.* FROM Question JOIN Answer "
                + "ON Question.questionId=Answer.questionId WHERE Answer.answId=? ORDER BY Answer.createAt DESC";
        return getListBySQL(sqlString, answererId);
    }

    public List<Question> getNoAnswerQuestions() {
        String sql = "SELECT Question.* FROM Question LEFT JOIN Answer "
                + "ON Question.questionId=Answer.aid WHERE Answer.aid = NULL ";
        return getListBySQL(sql);
    }

    /**
     * 根据回答者的关键字寻找未解决问题
     *
     * @param anserId
     * @return
     */
    public List<Question> getNoAnswerQuestionsByKeyWord(String anserId) {
//        String sql = "SELECT Question.* FROM Keywordanswer,"+
//                     " Question LEFT JOIN Answer ON  Question.questionId=Answer.questionId" +
//                     " WHERE Answer.aid = NULL" +
//                     " AND Question.keywordId = Keywordanswer.kaId" +
//                     " AND Keywordanswer.answId = ?";
        String sql = "SELECT Question.* FROM KeywordAnswer," +
                " Question" +
                " WHERE Question.isAnswered = 0" +
                " AND Question.keywordId = KeywordAnswer.kaId" +
                " AND KeywordAnswer.answId = ? LIMIT 10";
        return getListBySQL(sql, anserId);

    }

    public List<Question> getListByColumnValue(String colName, Object colValue) {
        String hqlString = "from Question q where q." + colName + "=?";
        return getListByHQL(hqlString, colValue);
    }

    public List<Question> getUndispatchedQuestion(String mediaId) {
        List<Question> list = getListByHQL("from Question q where q.isDispatched=0 AND q" +
                ".mediaInfo.mediaId=?", mediaId);
        return list;
    }

    public List<Question> getQuestionByQuesId(String questionerId) {
        return getListByColumnValue("questioner.id", Integer.parseInt(questionerId));
    }


}
