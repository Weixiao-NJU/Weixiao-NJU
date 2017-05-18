package org.wx.weixiao.util.scheduletask;

import java.time.LocalDateTime;

/**
 * Created by Daniel on 2017/2/20.
 */
public class TaskEntity {
    private String name;
    //read task
    private Runnable runnable = null;

    private boolean repeatable = false;

    private LocalDateTime runTime = null;

    private long intervalMillis = -1;

    private LocalDateTime createTime;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public boolean getRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public LocalDateTime getRunTime() {
        return runTime;
    }

    public void setRunTime(LocalDateTime runTime) {
        this.runTime = runTime;
    }

    public long getIntervalMillis() {
        return intervalMillis;
    }

    public void setIntervalMillis(long intervalMillis) {
        this.intervalMillis = intervalMillis;
    }
}
