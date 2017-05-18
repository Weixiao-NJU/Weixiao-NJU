package org.wx.weixiao.service;

import org.wx.weixiao.message.resp.BaseMessage;

/**
 * Created by Daniel on 2017/4/26.
 */
public interface TuringRobotService {
    /**
     * 根据信息和用户id调用图灵机器人，返回结果
     *
     * @param info   查询的信息
     * @param userId 用户id
     * @param loc    地址信息，可以为空
     * @return
     */
    BaseMessage getResult(String info, String mediaId, String userId, String loc);
}
