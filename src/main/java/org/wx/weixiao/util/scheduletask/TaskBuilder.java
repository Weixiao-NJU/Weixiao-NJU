package org.wx.weixiao.util.scheduletask;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by Daniel on 2017/2/20.
 * 任务创建器,用来创建可以管理运行的类，必须添加任务和设定执行
 */
public class TaskBuilder {
    private TaskEntity taskEntity;

    private TaskBuilder() {
        taskEntity = new TaskEntity();
        taskEntity.setRepeatable(false);
    }

    /**
     * create a new taskbuilder,in order to create a new TaskEntity
     * the default task is not repeatable.
     *
     * @return
     */
    public static TaskBuilder createBuilder() {
        return new TaskBuilder();
    }

    public TaskBuilder addTask(Runnable runnable) {
        taskEntity.setRunnable(runnable);
        return this;
    }

    public TaskBuilder setName(String name) {
        taskEntity.setName(name);
        return this;
    }

    /**
     * 设置为一直重复的任务
     *
     * @return
     */
    public TaskBuilder asRepeatableTask() {
        taskEntity.setRepeatable(true);
        return this;
    }

    /**
     * 设定第一次运行时间
     *
     * @param date   日期
     * @param format 自定义的日期转换格式 具体格式参见{@link DateTimeFormatter}
     * @return
     */
    public TaskBuilder firstRunDateTime(String date, String format) {
        return firstRunDateTime(LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format)));
    }

    /**
     * 设定第一次运行的时间
     *
     * @param time
     * @return
     */
    public TaskBuilder firstRunDateTime(LocalDateTime time) {
        taskEntity.setRunTime(time);
        return this;
    }

    /**
     * 设定第一次运行的时间，默认为今天，如果今天时间已过则从明天开始
     *
     * @param time
     * @return
     */
    public TaskBuilder firstRunTime(LocalTime time) {
        LocalDate runDate = LocalDate.now();
        if (Duration.between(LocalTime.now(), time).toMillis() < 0) {
            runDate = runDate.plusDays(1);
        }
        return firstRunDateTime(LocalDateTime.of(runDate, time));
    }

    /**
     * 参见firstRunTime
     *
     * @param time
     * @param format 具体格式参见{@link DateTimeFormatter}
     * @return
     */
    public TaskBuilder firstRunTime(String time, String format) {
        return firstRunTime(LocalTime.parse(time, DateTimeFormatter.ofPattern(format)));
    }

    /**
     * 参见firstRunTime 采用默认的HH:mm:ss格式，24小时制
     *
     * @param time
     * @return
     */
    public TaskBuilder firstRunTime(String time) {
        return firstRunTime(LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    /**
     * 运行频率
     *
     * @param frequency 运行频率
     * @param timeUnit  时间单位
     * @return
     */
    public TaskBuilder frequency(int frequency, TimeUnit timeUnit) {
        taskEntity.setIntervalMillis(timeUnit.toMillis(1) / frequency);
        return this;
    }

    /**
     * 任务间隔时间，跟frequency冲突
     *
     * @param counts   对应时间单位的单位数
     * @param timeUnit 时间单位
     * @return
     */
    public TaskBuilder intervals(int counts, TimeUnit timeUnit) {
        taskEntity.setIntervalMillis(timeUnit.toMillis(1) * counts);
        return this;
    }


    public TaskEntity build() throws Exception {
        Optional.ofNullable(taskEntity.getRunnable()).orElseThrow(() -> new Exception("任务为空"));
        if (taskEntity.getRunTime() == null)
            taskEntity.setRunTime(LocalDateTime.now());
        if (taskEntity.getRepeatable() && taskEntity.getIntervalMillis() <= 0) {
            throw new Exception("时间间隔不能小于0");
        }
        return taskEntity;
    }

}
