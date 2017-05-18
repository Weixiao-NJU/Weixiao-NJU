package org.wx.weixiao.service;


import org.wx.weixiao.Info.LectureInfo;
import org.wx.weixiao.Info.LectureSubscriberInfo;

import java.util.List;

/**
 * Created by darxan on 2017/4/25.
 */
public interface LectureService {

    /**
     * 讲座信息列表
     * 按照时间倒排
     * @param all 是否需要包括 已经结束的讲座
     * @param page page>0 表示根据pageSize的分页码，返回第page页讲座；page<=0,表示不分页，返回所有讲座
     * @param pageSize 每页大小 ：pageSize<1时，默认为10
     * @return
     */
    List<LectureInfo> lectureList(boolean all, int page, int pageSize);

    /**
     * 获取讲座总页数
     */
    int getTotalPage(boolean all,int pageSize);


    /**
     * 详细的讲座信息
     * @param lid
     * @return
     */
    LectureInfo lecture(int lid);


    /**
     * 报名并请求提醒
     * @param applicant 报名人信息
     * @return
     */
    void subscribe(LectureSubscriberInfo applicant);

    /**
     * 对某一讲座感兴趣
     * @param lid
     * @return
     */
    void interest(String openId,int lid);

    /**
     *
     * @param lid
     * @return
     */
    List<LectureSubscriberInfo> getSubscribers(int lid);

    boolean isInterest(String openId,int lid);

    boolean isSubscriber(String openId,int lid);

    List<LectureInfo> getInterestList(String openId);
    List<LectureInfo> getSubscriberList(String openId);



}

