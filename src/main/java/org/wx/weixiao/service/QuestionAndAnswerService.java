package org.wx.weixiao.service;

import org.wx.weixiao.Info.AnswerInfo;
import org.wx.weixiao.Info.AnswererInfo;
import org.wx.weixiao.Info.QuestionInfo;
import org.wx.weixiao.model.Question;

import java.util.List;

/**
 * Created by zs14 on 2016/12/28.
 */
public interface QuestionAndAnswerService {
    /**
     * 提交问题
     */
    public int addQuestion(QuestionInfo info);

    /**
     * 提交回答
     */
    public int addAnswer(AnswerInfo info);

    /**
     * 根据用户提出的问题ID获取该问题的具体的内容
     */
    public QuestionInfo searchQuestion(String questionId);

    /**
     * 根据问题ID获取该问题对应的回答列表
     */
    public List<AnswerInfo> getAnswers(String questionId);

    /**
     * 1.问题的发送 在问题被提交后 进行方法调用
     * 2.定时任务 一定时间内没有回答问题进行调用
     * @param q
     * @return
     */
    public int dispatch(Question q);

    /**
     * 教师进行转发问题
     * @param fromAnswerer 转发问题方
     * @param toAnswerer 接收问题方
     * @param questionId 问题编号
     * @return
     */
    public int dispatchQuestion(AnswererInfo fromAnswerer,AnswererInfo toAnswerer,String questionId);


    /**
     *每天早上把前一天工作时间以外学生问的问题推送给老师

     */
    public void dispatchEveryDay(String mediaId);

}
