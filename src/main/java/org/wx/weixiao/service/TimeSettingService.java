package org.wx.weixiao.service;

/**
 * Created by lizhimu on 2017/1/7.
 */
public interface TimeSettingService {
    public void setTime(String startTime,String endTime,String mediaId);

    public boolean isBetweenTime(String mediaId);

    public String readStartTime(String mediaId);

    public String readEndTime(String mediaId);
}
