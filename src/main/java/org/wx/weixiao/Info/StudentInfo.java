package org.wx.weixiao.Info;

/**
 * Created by Daniel on 2016/12/28.
 */
public class StudentInfo {
    private String weixiao_id;
    private String card_number;
    private String name;
    private int grade;
    private String college;
    private String prefession;

    public String getWeixiao_id() {
        return weixiao_id;
    }

    public void setWeixiao_id(String weixiao_id) {
        this.weixiao_id = weixiao_id;
    }

    public String getCard_num() {
        return card_number;
    }

    public void setCard_num(String card_num) {
        this.card_number = card_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPrefession() {
        return prefession;
    }

    public void setPrefession(String prefession) {
        this.prefession = prefession;
    }
}
