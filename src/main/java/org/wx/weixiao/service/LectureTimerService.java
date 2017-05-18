package org.wx.weixiao.service;

import java.util.Date;

/**
 * Created by darxan on 2017/4/26.
 */
public interface LectureTimerService {

    /**
     * 通过调用API，更新本地服务器存储的lectures
     */
    void updateLectures();


    /**
     * 定期查找并以email通知需要被提醒的订阅者
     */
    void emailSubscribers(long before);


    /**
     * 定期查找并以短信通知需要被提醒的订阅者
     *
     */
    @SuppressWarnings("no works")
    void textSubscribers();

}
