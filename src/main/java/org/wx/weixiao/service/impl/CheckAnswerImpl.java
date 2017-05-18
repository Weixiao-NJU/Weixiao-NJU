package org.wx.weixiao.service.impl;

import org.springframework.stereotype.Component;
import org.wx.weixiao.dao.DAOManager;
import org.wx.weixiao.model.Answerer;
import org.wx.weixiao.service.CheckAnswererService;

/**
 * Created by lizhimu on 2016/12/27.
 */
@Component("CheckAnswerService")
public class CheckAnswerImpl implements CheckAnswererService {


    @Override
    public boolean checkValidateAnswerer(String secretWord, String FromUserName) {
        //首先从Answerer表查询密钥，如果查询到密钥，那么更新此用户的openID，返回 true
        //如果没有查询到密钥，返回false
        Answerer answer = DAOManager.answererDao.getAnswererBySecretWord(secretWord);
        if (answer == null) {
            return false;
        } else {
            answer.setOpenId(FromUserName);
            answer.setSecretWord(null);
            DAOManager.answererDao.update(answer);
            return true;
        }
    }




}
