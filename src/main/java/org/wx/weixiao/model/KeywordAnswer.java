package org.wx.weixiao.model;

import javax.persistence.*;

/**
 * Created by Daniel on 2016/12/27.
 */
@Entity
public class KeywordAnswer implements FakeDeletable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer kaId;

    private String keyword;

    @ManyToOne
    @JoinColumn(name = "answId")
    private Answerer answerer;

    /**
     * 是否被删除
     */
    @Column(columnDefinition = "INT default 0")
    private Integer isDelete;

    @Override
    public Integer getIsDelete() {
        return isDelete;
    }

    @Override
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getKaId() {
        return kaId;
    }

    public void setKaId(Integer kaId) {
        this.kaId = kaId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Answerer getAnswerer() {
        return answerer;
    }

    public void setAnswerer(Answerer answerer) {
        this.answerer = answerer;
    }
}
