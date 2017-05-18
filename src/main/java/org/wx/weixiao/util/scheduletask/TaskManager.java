package org.wx.weixiao.util.scheduletask;

import org.apache.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Daniel on 2017/2/20.
 * 任务管理器
 */
public class TaskManager {
    /**
     * 任务运行线程
     */
    private static ScheduledExecutorService scheduledExecutorService;
    /**
     * 核心数，默认为双核
     */
    private static int CORE_NUM = 2;
    private static Map<String, ScheduledFuture> taskFutures;
    private static Map<String, TaskEntity> taskEntityMap;

    private static int count = 0;

    private static Logger logger;
    /**
     * 默认任务的名字
     */
    private static final String MONITOR = "DEFAULT_MONITOR";
    /**
     * 默认为5分钟执行一次监控打印
     */
    private static int MONITOR_COUNTS = 5;
    private static TimeUnit MONITOR_TIME_UNIT = TimeUnit.MINUTES;

    static {
        scheduledExecutorService = Executors.newScheduledThreadPool(CORE_NUM);
        taskFutures = new HashMap<>();
        taskEntityMap = new HashMap<>();
    }

    private TaskManager() {
    }

    /**
     * 注册任务
     *
     * @param taskEntity     任务实体，可以通过{@link TaskBuilder}创建
     * @param isInterrupting 是否可以打断已经存在的相同名字的任务。
     *                       置为true时会打断正在调度的任务中跟新任务名字相同的任务
     */
    public static void registerTask(TaskEntity taskEntity, boolean
            isInterrupting) {

        if (taskEntity.getName() == null)
            taskEntity.setName("Task" + getCount());
        String taskName = taskEntity.getName();
        //不能和监控任务有相同的名字
        if (taskName.equals(MONITOR))
            return;
        if (taskFutures.get(taskName) != null && isInterrupting) {
            taskFutures.get(taskName).cancel(true);
        } else if (!isInterrupting) {
            return;
        }
        long timeMillis = Duration.between(LocalDateTime.now(), taskEntity.getRunTime()).toMillis();
        ScheduledFuture scheduledFuture;
        taskEntity.setCreateTime(LocalDateTime.now());
        if (taskEntity.getRepeatable()) {
            scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(taskEntity
                            .getRunnable(), timeMillis,
                    taskEntity.getIntervalMillis(), TimeUnit.MILLISECONDS);
        } else {
            scheduledFuture = scheduledExecutorService.schedule(taskEntity.getRunnable(),
                    timeMillis, TimeUnit
                            .MILLISECONDS);
        }
        taskFutures.put(taskName, scheduledFuture);
        taskEntityMap.put(taskName, taskEntity);
    }

    /**
     * 重载方法，默认为打断旧任务
     *
     * @param taskEntity
     */
    public static void registerTask(TaskEntity taskEntity) {
        registerTask(taskEntity, true);
    }

    /**
     * 停止某个任务
     *
     * @param taskName
     */
    public static void stopTask(String taskName) {
        if (contains(taskName)) {
            taskFutures.get(taskName).cancel(true);
            taskEntityMap.remove(taskName);
        }
    }

    /**
     * 判断是否有名字相同的任务
     *
     * @param taskName
     * @return
     */
    public static boolean contains(String taskName) {
        return taskEntityMap.keySet().contains(taskName);
    }

    /**
     * 获得默认线程的标号
     *
     * @return
     */
    private synchronized static int getCount() {
        count++;
        return count;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        TaskManager.logger = logger;
    }

    private static void print(String info) {
        if (logger != null)
            logger.info(info);
        else
            System.out.println(info);
    }

    /**
     * 启动监控任务，监控任务会在指定的时间间隔打印出当前的任务信息
     */
    public static void scheduleMonitor() {
        //已经启动监控
        if (contains(MONITOR))
            return;
        try {
            TaskEntity monitorEntity = TaskBuilder.createBuilder().addTask(() -> {
                taskFutures.forEach((k, v) -> {
                    if (v.isDone())
                        taskEntityMap.remove(k);
                });
                taskEntityMap.forEach((k, v) -> print(String.format
                        ("Name:%20s\trepeatable:%4s\trunTime%25s\tintervals:%10s" +
                                        "\tcreateTime:%25s",
                                v.getName(), v.getRepeatable(), v.getRunTime().toString(),
                                v.getIntervalMillis(), v.getCreateTime().toString())));
            }).setName(MONITOR).intervals(MONITOR_COUNTS, MONITOR_TIME_UNIT).asRepeatableTask().build();
            registerTask(monitorEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Runnable runnable = (() -> {
            System.out.println(LocalTime.now().toString());
        });
        TaskEntity taskEntity = TaskBuilder.
                createBuilder().setName("Task").
                addTask(runnable).
                asRepeatableTask().
                intervals(5, TimeUnit.SECONDS).
                build();
        TaskManager.registerTask(taskEntity);
        TaskManager.scheduleMonitor();
        TimeUnit.SECONDS.sleep(5);
        TaskManager.registerTask(taskEntity);
    }


}

