package org.wx.weixiao.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wx.weixiao.dao.DAOManager;
import org.wx.weixiao.message.resp.RespMessage;
import org.wx.weixiao.model.MediaInfo;
import org.wx.weixiao.service.AllDispatchCoreService;
import org.wx.weixiao.servlet.conf.AppConfig;
import org.wx.weixiao.util.ErrorCodeUtil;
import org.wx.weixiao.util.NameUtil;
import org.wx.weixiao.util.weixiaoapi.WeixiaoAPI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Daniel on 2016/12/19.
 */
@Component("AllDispatchHandler")
public class AllDispatchHandler extends AbstractHandler {
    @Autowired
    private AllDispatchCoreService allDispatchCoreService;


    @Override
    String trigger(HttpServletRequest request, HttpServletResponse response, AppConfig config) {
        // xml格式的消息数据
        Map<String, String> parameters = (Map<String, String>) request.getAttribute(NameUtil.PARAMETERS);
        String media_id = parameters.get(NameUtil.MEDIAID);
        MediaInfo mediaInfo = DAOManager.mediaInfoDao.getByMediaId(media_id);
        MediaInfo newMediaInfo = WeixiaoAPI.getMediaInfo(media_id, config);
        if (mediaInfo != null) {
            newMediaInfo.setMid(mediaInfo.getMid());
        }
        DAOManager.mediaInfoDao.saveOrUpdate(newMediaInfo);

        return allDispatchCoreService.handleRequest(request);
    }

    @Override
    String other(HttpServletRequest request, AppConfig config) {
        return "other";
    }

    @Override
    void openOperation(String mediaId, RespMessage rmsg, AppConfig config) {
        rmsg.setErrcode(ErrorCodeUtil.SUCCESS);
        rmsg.setErrmsg("ok");
        rmsg.setToken(config.getToken());
        rmsg.setIs_config(1);
    }

    @Override
    void closeOperation(String mediaId, RespMessage rmsg, AppConfig config) {
        MediaInfo mediaInfo = DAOManager.mediaInfoDao.getByMediaId(mediaId);
        if (mediaInfo != null)
            DAOManager.mediaInfoDao.fakeDelete(mediaInfo);
        rmsg.setCodeAndMsg(ErrorCodeUtil.SUCCESS, "ok");
    }

    @Override
    void configOperation(HttpServletRequest request, String mediaId, RespMessage rmsg, AppConfig
            config) {
//        MediaInfo mediaInfo = DAOManager.mediaInfoDao.getByMediaId(mediaId);
//        MediaInfo newMediaInfo = WeixiaoAPI.getMediaInfo(mediaId, config);
//        if (mediaInfo != null) {
//            newMediaInfo.setMid(mediaInfo.getMid());
//        }
//        request.getRequestDispatcher(String.format("%s/core/ask_config?media_id=%s", ServerUtil
//                .SERVER_ADDRESS, mediaId));
//        DAOManager.mediaInfoDao.saveOrUpdate(newMediaInfo);
        rmsg.setCodeAndMsg(ErrorCodeUtil.SUCCESS, "ok");
    }

    @Override
    void keywordOperation(HttpServletRequest request, RespMessage rmsg, AppConfig config) {
        rmsg.setCodeAndMsg(ErrorCodeUtil.SUCCESS, "ok");
    }
}
