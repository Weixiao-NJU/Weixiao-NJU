package org.wx.weixiao.service;

import org.wx.weixiao.Info.KnowledgeInfo;
import org.wx.weixiao.service.vo.ReQuestionVO;

import java.util.List;

/**
 * Created by wangjiawei on 2016/12/15.
 */
public interface KnowledgeBaseService {
    //TODO ?要不要返回问题ID，错误返回-1
    /**
     * 加入新的问题和答案
     * @param knowledgeInfo
     * @return ErrorCodeUtil中的错误码
     */
    public int storeQuestion(KnowledgeInfo knowledgeInfo);

    /**
     * 查询搜索匹配的第一个问题答案
     * @param question 问题文本
     * @param imageType 图片类型，1代表大图片，0代表小图片
     * @return 一个被查询出来的QuestionDoc
     */
    public ReQuestionVO searchFirstAnswer(String question,int imageType);


    /**
     * 返回常见问题列表
     * @return
     */
    public List<ReQuestionVO> searchNormalAnswer();
    /**
     * 通过id获取到相对应的知识库
     * @param id
     * @return
     */
    public KnowledgeInfo getKnowledgeBaseById(int id);

    /**
     * 查询指定个数的问题答案
     * @param question
     * @param number
     * @return 返回问题列表
     */
    public List<ReQuestionVO> searchAnswerNum(String question, int number);

    /**
     * 获取知识库列表
     * 根据MediaId获取知识库
     * @return
     */
    public List<KnowledgeInfo> getAllKnowledge(String mediaId);

    /**
     * 获取热门知识库列表
     * 根据MediaId获取知识库
     * @return
     */
    public List<KnowledgeInfo> getHeatKnowledge(String mediaId);

    /**
     * 根据answererId获取MediaID
     * @param answererId
     * @return
     */
    public String getMediaIdbyAnswererId(String answererId);

    /**
     * 根据传入的知识库更新现有知识库，必须传入ID
     * @return
     */
    public int updateKnowledge(KnowledgeInfo knowledgeInfo);

    /**
     * 根据传入的知识库ID删除知识库
     * @param knowledgeID
     * @return
     */
    public int deleteKnowledge(String knowledgeID);

}
