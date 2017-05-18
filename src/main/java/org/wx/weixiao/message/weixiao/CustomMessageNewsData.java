package org.wx.weixiao.message.weixiao;

import java.util.List;

/**
 * Created by darxan on 2016/12/30.
 */
public class CustomMessageNewsData extends CustomMessageBaseData {

    private List<NewsContent> news;

    public CustomMessageNewsData() {
        super("news");
    }

    public List<NewsContent> getNews() {
        return news;
    }

    public void setNews(List<NewsContent> news) {
        this.news = news;
    }
}
