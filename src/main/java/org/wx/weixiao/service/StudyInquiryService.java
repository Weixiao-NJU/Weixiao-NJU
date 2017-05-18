package org.wx.weixiao.service;

import org.wx.weixiao.Info.QuestionInfo;

import java.util.List;

/**
 * Created by Jerry Wang on 2017/1/14.
 */
public interface StudyInquiryService {
    /**
     * 根据用户openId来找到该用户提出的所有问题
     * @param openId
     * @return 问题列表
     */
    public List<QuestionInfo> getAllQuestion(String openId);

    /**
     * 根据公众号Id获得该公众号近时间段内的公开提问
     * @param mediaId
     * @return
     */
    public List<QuestionInfo> getRecentOpenQuestion(String mediaId);


    /**
     * 根据回答者Id获取所有回答者回答过得问题
     * @param answererId
     * @return
     */
    public List<QuestionInfo> getMyAnswers(String answererId);


    /**
     * 获取现在回答者应该解决但是还未解决的问题
     * @param answererId
     * @return
     */
    public List<QuestionInfo> getUnsolvedQuestions(String answererId);

    /**
     * 获取一个公众号还没有解决需要分派的问题
     * @param media_id
     * @return
     */
    public List<QuestionInfo> getUnsolvedQuestionOnDuty(String media_id,String answerer_id);
}
