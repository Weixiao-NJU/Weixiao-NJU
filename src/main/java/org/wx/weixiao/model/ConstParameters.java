package org.wx.weixiao.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 * Created by Daniel on 2017/1/7.
 */
@Entity
public class ConstParameters implements FakeDeletable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cpId;

    private String work_time_start;

    private String work_time_end;

    /**
     * 是否被删除
     */
    @Column(columnDefinition = "INT default 0")
    private Integer isDelete;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mediaId")
    private MediaInfo mediaInfo;

    public String getWork_time_start() {
        return work_time_start;
    }

    public void setWork_time_start(String work_time_start) {
        this.work_time_start = work_time_start;
    }

    public String getWork_time_end() {
        return work_time_end;
    }

    public void setWork_time_end(String work_time_end) {
        this.work_time_end = work_time_end;
    }

    public MediaInfo getMediaInfo() {
        return mediaInfo;
    }

    public void setMediaInfo(MediaInfo mediaInfo) {
        this.mediaInfo = mediaInfo;
    }

    public Integer getCpId() {
        return cpId;
    }

    public void setCpId(Integer cpId) {
        this.cpId = cpId;
    }

    @Override
    public Integer getIsDelete() {
        return isDelete;
    }

    @Override
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
