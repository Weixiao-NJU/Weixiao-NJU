package org.wx.weixiao.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.wx.weixiao.dao.DAOManager;
import org.wx.weixiao.model.ConstParameters;
import org.wx.weixiao.service.TaskFactory;
import org.wx.weixiao.util.ApplicationContextHelper;
import org.wx.weixiao.util.scheduletask.TaskEntity;
import org.wx.weixiao.util.scheduletask.TaskManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by Daniel on 2016/12/31.
 */
public class StartUpServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        ApplicationContextHelper.setApplicationContext(ctx);
        System.out.println("server started");

        DAOManager.mediaInfoDao.getMediaInfos().forEach(e -> {
            String mediaId = e.getMediaId();
            ConstParameters constParameters = DAOManager.constParameterDao.readParameters(mediaId);
            if (constParameters == null || constParameters.getWork_time_start() == null) {
                return;
            }
            TaskEntity taskEntity = TaskFactory.createDispatchTask(mediaId, constParameters
                    .getWork_time_start());
            TaskManager.registerTask(taskEntity);
        });

        //TaskEntity timerEmail = TaskFactory.createLectureEmailTask();
        //TaskEntity lectureUpdate = TaskFactory.createLectureUpdateTask();
        //TaskManager.registerTask(timerEmail);
//        TaskManager.registerTask(lectureUpdate);

      //  TaskManager.setLogger(Logger.getLogger(TaskManager.class));
      //  TaskManager.scheduleMonitor();

    }
}
