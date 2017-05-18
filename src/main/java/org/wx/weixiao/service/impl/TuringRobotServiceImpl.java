package org.wx.weixiao.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wx.weixiao.dao.UserAccessTimeDao;
import org.wx.weixiao.exception.turingrobot.InfoNullException;
import org.wx.weixiao.exception.turingrobot.KeyGeneratingException;
import org.wx.weixiao.exception.turingrobot.RequestResultException;
import org.wx.weixiao.exception.turingrobot.UserNullException;
import org.wx.weixiao.message.resp.Article;
import org.wx.weixiao.message.resp.BaseMessage;
import org.wx.weixiao.message.resp.NewsMessage;
import org.wx.weixiao.message.resp.TextMessage;
import org.wx.weixiao.model.UserAccessTime;
import org.wx.weixiao.service.TuringRobotService;
import org.wx.weixiao.util.ImgUtil;
import org.wx.weixiao.util.MessageUtil;
import org.wx.weixiao.util.turingapi.RetCode;
import org.wx.weixiao.util.turingapi.TuringAPI;
import org.wx.weixiao.util.turingapi.TuringInput;
import org.wx.weixiao.util.turingapi.TuringKeyGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by Daniel on 2017/4/26.
 */
@Service
public class TuringRobotServiceImpl implements TuringRobotService {
    @Autowired
    private TuringKeyGenerator turingKeyGenerator;
    @Autowired
    private UserAccessTimeDao userAccessTimeDao;
    /**
     * 返回码与对应转换方式的表
     */
    private Map<Integer, Function<JsonObject, BaseMessage>> transMap;

    private static final String LONG_ACCESS_TIPS = "您可以输入\"提问:\"+提问内容（如 提问:跨专业准入 ）直接进行提问哦！";
    private static final String info_null_tips = "您没有发送任何信息哦！（输入\"帮助\"查看所有功能）";
    private static final String server_error_tips = "服务请求失败，请稍后再试";
    private static final long LONG_INTERVAL_TIMEMILISS = 1000 * 3600 * 24;

    public TuringRobotServiceImpl() {
        transMap = new HashMap<>();
        transMap.put(RetCode.TEXT.getCode(), new TextTransfer());
        transMap.put(RetCode.URL.getCode(), new URLTransfer());
        transMap.put(RetCode.NEWS.getCode(), new NewsTransfer());
        transMap.put(RetCode.MENU.getCode(), new MenuTransfer());
        Function<JsonObject, BaseMessage> function = (e -> {
            TextMessage textMessage = new TextMessage();
            textMessage.setContent(server_error_tips);
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            return textMessage;
        });
        transMap.put(RetCode.KEY_ERROR.getCode(), function);
        transMap.put(RetCode.INFO_NULL.getCode(), function);
        transMap.put(RetCode.NO_TIMES.getCode(), function);
        transMap.put(RetCode.FORMAT_WRONG.getCode(), function);
    }

    @Override
    public BaseMessage getResult(String info, String mediaId, String userId, String loc) {
        String result = null;
        UserAccessTime userAccessTime = userAccessTimeDao.get(userId);
        if (userAccessTime == null) {
            userAccessTime = new UserAccessTime();
            userAccessTime.setUserId(userId);
            userAccessTime.setTimeMillis(0);
        }
        boolean longAccessInterval = longAccessInterval(userAccessTime.getTimeMillis());
        userAccessTime.setTimeMillis(System.currentTimeMillis());
        userAccessTimeDao.saveOrUpdate(userAccessTime);
        try {
            TuringInput input = packageInput(info, userId, loc);
            JsonObject jsonObject = TuringAPI.getQueryResult(input);
            BaseMessage returnMessage = transMap.get(jsonObject.get("code").getAsInt()).apply
                    (jsonObject);
            if (longAccessInterval) {
                returnMessage = addExtraInfo(returnMessage);
            }
            return returnMessage;
        } catch (InfoNullException e) {
            e.printStackTrace();
            result = info_null_tips;
        } catch (UserNullException | KeyGeneratingException | RequestResultException e) {
            e.printStackTrace();
            result = server_error_tips;
        }

        return errorResult(result);
    }

    /**
     * 是否长时间未访问公众号，设定为1天的时间
     *
     * @param lastTime
     * @return
     */
    private boolean longAccessInterval(long lastTime) {
        return (System.currentTimeMillis() - lastTime) >= LONG_INTERVAL_TIMEMILISS;
    }

    private BaseMessage addExtraInfo(BaseMessage baseMessage) {
        TextMessage textMessage = (TextMessage) baseMessage;
        textMessage.setContent(textMessage.getContent() + "\n" + LONG_ACCESS_TIPS);
        return textMessage;
    }

    /**
     * 打包信息，将信息打包为调用API需要的类
     *
     * @param info
     * @param userId
     * @param loc
     * @return
     * @throws InfoNullException
     * @throws UserNullException
     * @throws KeyGeneratingException
     */
    private TuringInput packageInput(String info, String userId, String loc) throws
            InfoNullException, UserNullException, KeyGeneratingException {
        if (info == null || info.trim().length() == 0)
            throw new InfoNullException();
        if (userId == null || userId.length() == 0)
            throw new UserNullException();
        String key;
        key = turingKeyGenerator.getKey(userId);
        TuringInput input = new TuringInput();
        input.setKey(key);
        input.setInfo(info);
        input.setLoc(loc);
        input.setUserid(userId);
        return input;
    }

    /**
     * @param result
     * @return
     */
    private TextMessage errorResult(String result) {
        TextMessage textMessage = new TextMessage();
        textMessage.setContent(result);
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        return textMessage;
    }

    /**
     * 文本数据的转换
     */
    class TextTransfer implements Function<JsonObject, BaseMessage> {
        @Override
        public BaseMessage apply(JsonObject jsonObject) {
            TextMessage textMessage = new TextMessage();
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            try {
                textMessage.setContent(jsonObject.get("text").getAsString());
            } catch (Exception e) {
                e.printStackTrace();
                textMessage.setContent(server_error_tips);
            }
            return textMessage;
        }
    }

    /**
     * 链接数据的转换
     */
    class URLTransfer implements Function<JsonObject, BaseMessage> {
        @Override
        public BaseMessage apply(JsonObject jsonObject) {
            TextMessage textMessage = new TextMessage();
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            try {
                String text = jsonObject.get("text").getAsString();
                String url = jsonObject.get("url").getAsString();
                textMessage.setContent(text + "：\n" + url);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return textMessage;
        }
    }

    /**
     * 新闻数据的转换
     */
    class NewsTransfer implements Function<JsonObject, BaseMessage> {
        @Override
        public BaseMessage apply(JsonObject jsonObject) {
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setArticles(new ArrayList<>());
            int count = 0;
            try {
                for (JsonElement jsonElement : jsonObject.get("list").getAsJsonArray()) {
                    count++;
                    Article article = new Article();
                    JsonObject newsItem = jsonElement.getAsJsonObject();
                    article.setTitle(newsItem.get("article").getAsString());
                    article.setUrl(newsItem.get("detailurl").getAsString());
                    if (count == 1)
                        article.setPicUrl(ImgUtil.NJU_QUESTION_BIG1);
                    else
                        article.setPicUrl(ImgUtil.NJU_QUESTION_SMALL);
                    newsMessage.getArticles().add(article);
                    if (count == 10)
                        break;
                }
                newsMessage.setArticleCount(count);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return newsMessage;
        }
    }

    /**
     * 新闻数据的转换
     */
    class MenuTransfer implements Function<JsonObject, BaseMessage> {
        @Override
        public BaseMessage apply(JsonObject jsonObject) {
            NewsMessage menuMessage = new NewsMessage();
            menuMessage.setArticles(new ArrayList<>());
            int count = 0;
            try {
                for (JsonElement jsonElement : jsonObject.get("list").getAsJsonArray()) {
                    count++;
                    Article article = new Article();
                    JsonObject newsItem = jsonElement.getAsJsonObject();
                    article.setTitle(newsItem.get("name").getAsString());
                    article.setUrl(newsItem.get("detailurl").getAsString());
                    article.setPicUrl(newsItem.get("icon").getAsString());
                    article.setDescription(newsItem.get("info").getAsString());
                    menuMessage.getArticles().add(article);
                    if (count == 10)
                        break;
                }
                menuMessage.setArticleCount(count);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return menuMessage;
        }
    }

}
