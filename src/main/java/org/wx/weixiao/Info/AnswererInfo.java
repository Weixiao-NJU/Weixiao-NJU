package org.wx.weixiao.Info;

import java.util.List;

/**
 * Created by zs14 on 2016/12/25.
 */
public class AnswererInfo {
    private String id;
    private String name;
    private String department;
    private String telephone;
    private List<String> keyword;
    private String  accountStr;
    private String mediaId;

    public AnswererInfo() {
    }


    public AnswererInfo(String id, String name, String department, String telephone, List<String> keyword, String accountStr) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.telephone = telephone;
        this.keyword = keyword;
        this.accountStr = accountStr;
    }

    public AnswererInfo(String id, String name, String department, String telephone, List<String> keyword, String accountStr, String mediaId) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.telephone = telephone;
        this.keyword = keyword;
        this.accountStr = accountStr;
        this.mediaId = mediaId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<String> keyword) {
        this.keyword = keyword;
    }

    public String getAccountStr() {
        return accountStr;
    }

    public void setAccountStr(String accountStr) {
        this.accountStr = accountStr;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String toString() {
        return "AnswererInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", telephone='" + telephone + '\'' +
                ", keyword=" + keyword +
                ", accountStr='" + accountStr + '\'' +
                '}';
    }
}
