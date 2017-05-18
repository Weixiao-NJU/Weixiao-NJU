package org.wx.weixiao.service;

import org.springframework.stereotype.Service;
import org.wx.weixiao.model.Answerer;

/**
 * Created by lizhimu on 2016/12/26.
 */

public interface CheckAnswererService {
    /**
     * 查询搜索匹配的第一个问题答案
     * @param keyWord 回答者输入的密钥
     * @param FromUserName 回答者的openID
     * @return 判断回答者输入的密钥是否正确
     */
    public boolean checkValidateAnswerer(String keyWord,String FromUserName);


}
