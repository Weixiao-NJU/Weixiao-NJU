package org.wx.weixiao.appConnection;

/**
 * Created by zs14 on 2016/12/24.
 */
public enum InquiryUrl {
    CLASSROOM("http://mapp.nju.edu.cn/wechat_app/free/www/app/index.html"),
    GRADE ("http://mapp.nju.edu.cn/wechat_app/grade/www/app/index.html"),
    WELCOME ("http://mapp.nju.edu.cn/wechat_app/newYear/www/app/welcome.html"),
    SCHEDULE("http://mapp.nju.edu.cn/wechat_app/schedule/www/app/index.html"),
    SPORT("http://mapp.nju.edu.cn/wechat_app/sport/www/app/index.html");

    // 定义私有变量
    private String url ;

    // 构造函数，枚举类型只能为私有
    private InquiryUrl(String url) {
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public void setUrl(String url){
        this.url=url;
    }

    @Override
    public String toString() {
        return this.url;
    }
}

