package org.wx.weixiao.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Daniel on 2017/5/5.
 */
@Entity
public class UserAccessTime {
    @Id
    private String userId;

    private long timeMillis;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }
}
