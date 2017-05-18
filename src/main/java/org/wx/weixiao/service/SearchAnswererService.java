package org.wx.weixiao.service;

import org.wx.weixiao.Info.AnswererInfo;
import org.wx.weixiao.Info.KeyWordInfo;

import java.util.List;

/**
 * Created by zs14 on 2016/12/26.
 * 做和Answerer相关的工作，包括增删改查等
 */
public interface SearchAnswererService {

    /**
     * 根据mediaID获取回答者列表
     * @return
     */
    public List<AnswererInfo> getAllAnswerer(String mId);

    /**
     * 根据mediaID获取回答者列表
     * @return
     */
    public List<AnswererInfo> getAllAnswererByMediaId(String mediaId);

    /**
     * 获取关键字列表
     * @return
     */
    public List<KeyWordInfo> getAllKeyWord(String mediaId);

    /**
     * 根据id获取回答者
     * @param answererId
     * @return
     */
    public AnswererInfo getAnswererById(String answererId);

    /**
     * 根据传入的AnswererInfo修改现有Answerer，必须有MediaId
     * @param answererInfo
     * @return
     */
    public int updateAnswerer(AnswererInfo answererInfo);

    /**
     * 根据answererId删除现有的Answerer
     * @param answererId
     * @return
     */
    public int deleteAnswerer(String answererId);

    /**
     * 根据传入的AnswererInfo增加Answerer
     * @param answererInfo
     * @return 返回增加的用户的AnswererId,失败返回-1
     */
    public int addAnswerer(AnswererInfo answererInfo);

}
