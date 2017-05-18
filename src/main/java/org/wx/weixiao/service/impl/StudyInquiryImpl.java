package org.wx.weixiao.service.impl;

import org.springframework.stereotype.Component;
import org.wx.weixiao.Info.QuestionInfo;
import org.wx.weixiao.dao.DAOManager;
import org.wx.weixiao.model.Question;
import org.wx.weixiao.model.Questioner;
import org.wx.weixiao.service.StudyInquiryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jerry Wang on 2017/1/14.
 */
@Component("StudyInquiryService")
public class StudyInquiryImpl implements StudyInquiryService {

    @Override
    public List<QuestionInfo> getAllQuestion(String openId) {
        //获取提问者
        Questioner questioner = DAOManager.questionerDao.getQuestionerbyOpenId(openId);
        if(questioner == null){
            return new ArrayList<>();
        }
        List<Question> re = DAOManager.questionDao.getQuestionByQuesId(questioner.getId()+"");

        return convertEntityToQuestion(re);
    }

    protected List<QuestionInfo> convertEntityToQuestion(List<Question> re) {
        List<QuestionInfo> questionInfos = re.stream().map(e->{
            QuestionInfo que = new QuestionInfo();
            que.setContent(e.getContent());
            que.setKeyword(e.getKeyword().getKeyword());
            que.setId(ImplHelper.generateCodebyQuestionId(e.getQuestionId()+""));
            que.setQuestion_media_id(e.getMediaInfo().getMediaId());
            return que;
        }).collect(Collectors.toList());
        return questionInfos;
    }

    @Override
    public List<QuestionInfo> getRecentOpenQuestion(String mediaId) {
        List<Question> re = DAOManager.questionDao.
                getRecentQuestionByMediaId(mediaId, 15);
        return convertEntityToQuestion(re);
    }

    @Override
    public List<QuestionInfo> getMyAnswers(String answererId) {
        List<Question> re = DAOManager.questionDao.getByAnswers(answererId);
        return convertEntityToQuestion(re);
    }

    @Override
    public List<QuestionInfo> getUnsolvedQuestions(String answererId) {
        List<Question> re = DAOManager.questionDao.getNoAnswerQuestionsByKeyWord(answererId);

        List<QuestionInfo> questionInfos = re.stream().map(e->{
            QuestionInfo que = new QuestionInfo();
            que.setContent(e.getContent());
            que.setKeyword(e.getKeyword().getKeyword());
            que.setId(ImplHelper.generateCodebyQid_Aid(e.getQuestionId()+"",answererId));
            que.setQuestion_media_id(e.getMediaInfo().getMediaId());
            return que;
        }).collect(Collectors.toList());
        return questionInfos;
    }

    @Override
    public List<QuestionInfo> getUnsolvedQuestionOnDuty(String media_id,String answerer_id) {
        List<Question> re = DAOManager.questionDao.getUnsolvedQuestionByMediaId(media_id);
        System.out.print(re.size()+answerer_id+"   "+media_id);
        List<QuestionInfo> questionInfos = re.stream().map(e->{
            QuestionInfo que = new QuestionInfo();
            que.setContent(e.getContent());
            que.setKeyword(e.getKeyword().getKeyword());
            que.setId(ImplHelper.generateCodebyQid_Aid(e.getQuestionId()+"",answerer_id));
            que.setQuestion_media_id(e.getMediaInfo().getMediaId());
            return que;
        }).collect(Collectors.toList());
        return questionInfos;
    }
}
