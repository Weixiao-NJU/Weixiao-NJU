package org.wx.weixiao.service;

import org.apache.log4j.Logger;
import org.wx.weixiao.service.impl.LectureTimerImpl;
import org.wx.weixiao.service.impl.QuestionAndAnswerImpl;
import org.wx.weixiao.util.ApplicationContextHelper;
import org.wx.weixiao.util.scheduletask.TaskBuilder;
import org.wx.weixiao.util.scheduletask.TaskEntity;
import org.wx.weixiao.util.scheduletask.WeixiaoTaskUtil;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Created by Daniel on 2017/2/27.
 */
public class TaskFactory {

    static QuestionAndAnswerService questionAndAnswerService;
    static Logger logger = org.apache.log4j.Logger.getLogger(TaskFactory.class);


    private static final String DISPATCH_TASK_NAME = "dispatch_question";
    private static final String SCAN_QUESTION_TASK = "scan_question";
    /**
     * {@link java.time.format.DateTimeFormatter}
     */
    private static final String TIME_FORMAT = "HH:mm";

    static {
        questionAndAnswerService = ApplicationContextHelper.getApplicationContext().getBean
                (QuestionAndAnswerImpl.class);
    }

    public static TaskEntity createDispatchTask(String media_id, String time) {
        try {
            return TaskBuilder.createBuilder().
                    setName(WeixiaoTaskUtil.getNewName(media_id, DISPATCH_TASK_NAME)).
                    addTask(() -> questionAndAnswerService.dispatchEveryDay(media_id)).
                    asRepeatableTask().
                    firstRunTime(time, TIME_FORMAT).
                    frequency(1, TimeUnit.DAYS).
                    build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TaskEntity createScanQuestionTask() {
        //// TODO: 2017/2/27  未完成
        return null;
    }


    private static final String LECTURE_TASK_NAME = "LECTURE_TASK";
    private static final String EMAIL_TASK_NAME = "EMAIL_TASK";

    private static LectureTimerService lectureTimerService;
    static {
        lectureTimerService = ApplicationContextHelper
                .getApplicationContext().getBean(LectureTimerImpl.class);
    }

    public static TaskEntity createLectureUpdateTask() {
        try {
            return TaskBuilder.createBuilder()
                    .setName(LECTURE_TASK_NAME)
                    .addTask(()->lectureTimerService.updateLectures())
                    .asRepeatableTask()
                    .firstRunDateTime(LocalDateTime.now())
                    .frequency(1, TimeUnit.HOURS)
                    .build();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TaskEntity createLectureEmailTask() {
        try {
            return TaskBuilder.createBuilder()
                    .setName(EMAIL_TASK_NAME)
                    .addTask(()->lectureTimerService.emailSubscribers(1000*24*60*60))
                    .asRepeatableTask()
                    .firstRunDateTime(LocalDateTime.now())
                    .frequency(1, TimeUnit.DAYS)
                    .build();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
