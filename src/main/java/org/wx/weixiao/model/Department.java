package org.wx.weixiao.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Daniel on 2016/12/26.
 */
@Entity
public class Department implements FakeDeletable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer deptId;

    private String name;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    private List<Answerer> answererList;

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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Answerer> getAnswererList() {
        return answererList;
    }

    public void setAnswererList(List<Answerer> answererList) {
        this.answererList = answererList;
    }
}
