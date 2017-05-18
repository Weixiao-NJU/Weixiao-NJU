package org.wx.weixiao.util.turingapi;

import org.wx.weixiao.exception.turingrobot.KeyGeneratingException;

/**
 * Created by Daniel on 2017/4/26.
 * 获得需要的key
 */
public interface TuringKeyGenerator {
    /**
     * 根据用户id返回对应的key
     * 应该保证同一个userId在一定时间段内得到的key是同一个
     * @param userId 用户id
     * @return
     * @throws KeyGeneratingException
     */
    String getKey(String userId) throws KeyGeneratingException;
}
