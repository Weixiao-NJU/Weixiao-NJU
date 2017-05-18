package org.wx.weixiao.service.impl;

import org.springframework.stereotype.Component;
import org.wx.weixiao.dao.DAOManager;
import org.wx.weixiao.model.ConstParameters;
import org.wx.weixiao.service.TaskFactory;
import org.wx.weixiao.service.TimeSettingService;
import org.wx.weixiao.util.DateUtil;
import org.wx.weixiao.util.scheduletask.TaskEntity;
import org.wx.weixiao.util.scheduletask.TaskManager;

/**
 * Created by lizhimu on 2017/1/7.
 */
@Component("TimeSettingService")
public class TimeSettinngImpl implements TimeSettingService {


    @Override
    public void setTime(String startTime, String endTime, String mediaId) {
        ConstParameters constParameters = DAOManager.constParameterDao.readParameters(mediaId);
        if (constParameters == null) {
            constParameters = new ConstParameters();
            constParameters.setMediaInfo(DAOManager.mediaInfoDao.getByMediaId(mediaId));
        }
        constParameters.setWork_time_start(startTime);
        constParameters.setWork_time_end(endTime);

        //重新注册任务
        TaskEntity taskEntity = TaskFactory.createDispatchTask(mediaId, startTime);
        TaskManager.registerTask(taskEntity);

        DAOManager.constParameterDao.saveOrUpdate(constParameters);
    }

    @Override
    public boolean isBetweenTime(String mediaId) {
        ConstParameters constParameter = DAOManager.constParameterDao.readParameters(mediaId);
        String currentTime = DateUtil.currentTimeToString();
        String startTime = constParameter.getWork_time_start();
        String endTime = constParameter.getWork_time_end();

        return DateUtil.isBetween(currentTime, startTime, endTime);
    }

    @Override
    public String readStartTime(String mediaId) {
        return DAOManager.constParameterDao.readStartTime(mediaId);
    }

    @Override
    public String readEndTime(String mediaId) {
        return DAOManager.constParameterDao.readEndTime(mediaId);
    }
}
