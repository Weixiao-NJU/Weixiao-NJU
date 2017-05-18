package org.wx.weixiao.util.turingapi;

/**
 * Created by Daniel on 2017/4/26.
 * 向api请求时发送的数据，loc为可选参数，其余为必须的参数
 */
public class TuringInput {
    /**
     * 机器人的apikey
     */
    private String key;
    /**
     * 请求内容，编码方式为UTF-8
     */
    private String info;
    /**
     * 位置信息，请求跟地理位置相关的内容时使用，编码方式UTF-8
     */
    private String loc;
    /**
     * 开发者给自己的用户分配的唯一标志（对应自己的每一个用户）,可实现上下文对话
     */
    private String userid;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
