package org.wx.weixiao.service.impl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wx.weixiao.dao.DAOManager;
import org.wx.weixiao.message.resp.Article;
import org.wx.weixiao.message.resp.BaseMessage;
import org.wx.weixiao.message.resp.NewsMessage;
import org.wx.weixiao.message.resp.TextMessage;
import org.wx.weixiao.model.Answerer;
import org.wx.weixiao.service.AllDispatchCoreService;
import org.wx.weixiao.service.CheckAnswererService;
import org.wx.weixiao.service.KnowledgeBaseService;
import org.wx.weixiao.service.TuringRobotService;
import org.wx.weixiao.service.vo.ReQuestionVO;
import org.wx.weixiao.util.DocumentHelper;
import org.wx.weixiao.util.ImgUtil;
import org.wx.weixiao.util.MessageUtil;
import org.wx.weixiao.util.ServerUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

/**
 * Created by darxan on 2016/12/20.
 */
@Component("AllDispatchCoreService")
public class AllDispatchCoreImpl implements AllDispatchCoreService {

    @Autowired
    private KnowledgeBaseService searchQuestion;
    @Autowired
    private CheckAnswererService answererService;
    @Autowired
    private TuringRobotService turingRobotService;

    public AllDispatchCoreImpl() {
    }

    /***
     * 特定约定前缀标识
     * 只有符合约定格式的问题才能被识别
     * 否则全部转入提问模块
     */
    private static final String[] BIND_MARKS = {"回答者绑定：", "回答者绑定:"};
    private static final String[] INQUIRY_MARK = {"学业咨询"};
    private static final String[] ON_DUTY = {"今天我值班"};
    private static final String[] QUESTION = {"提问:", "提问："};
    private static final String[] LECTURES = {"讲座查询"};
    private static final String[] HELP = {"帮助", "?","？"};

    private Map<String[], Function<PackageInput, BaseMessage>> startWithKeyWordsMethod;
    private Map<String[], Function<PackageInput, BaseMessage>> equalKeyWordsMethod;

    {
        startWithKeyWordsMethod = new HashMap<>();
        equalKeyWordsMethod = new HashMap<>();

        equalKeyWordsMethod.put(INQUIRY_MARK, e -> inqueryStudy(e.media_id, e.open_id));
        equalKeyWordsMethod.put(ON_DUTY, e -> onduty(e.media_id, e.open_id));
        equalKeyWordsMethod.put(LECTURES, e -> inqueryLecture(e.media_id, e.open_id));
        equalKeyWordsMethod.put(HELP, e -> getDocument());

        startWithKeyWordsMethod.put(BIND_MARKS, e -> validateKeyWord(e.content, e.open_id));
        startWithKeyWordsMethod.put(QUESTION, e -> getAnswer(e.content, e.media_id, e.open_id));


    }

    private static final int DEFAULT_ANSWER_NUMBER = 4;


    public String handleRequest(HttpServletRequest request) {

        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> parameters = MessageUtil.parseXml(request);
            return handleInput(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 总处理消息逻辑，中心控制转发
     *
     * @param parameters
     * @return
     */
    public String handleInput(Map<String, String> parameters) {
        //请求消息
        System.out.println(new Gson().toJson(parameters));

        String rspConent;

        // 发送方帐号
        String fromUserName = parameters.get("FromUserName");
        // 开发者微信号
        String toUserName = parameters.get("ToUserName");
        // 消息类型
        String msgType = parameters.get("MsgType");
        //返回消息
        BaseMessage message = null;

        //判断事件类型进行相应处理转发操作
        if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
            //文本文件 直接进行文本处理
            message = textAnalysis(parameters);
        } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
            //事件解析 进行事件处理
            message = eventAnalysis(parameters);
        } else {
            //其他类型 暂时不接受处理请求
            message = getErrorMessage(parameters);
        }

        message.setCreateTime(new Date().getTime());
        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);

        // 将消息对象转换成xml字符串
        rspConent = MessageUtil.messageToXml(message);

        return rspConent;
    }

    /**
     * 文本解析的总方法
     */
    protected BaseMessage textAnalysis(Map<String, String> parameters) {

        String content = parameters.get("Content");
        // 发送方帐号
        String open_id = parameters.get("FromUserName");
        // 开发者微信号
        String media_id = parameters.get("ToUserName");
        PackageInput packageInput = new PackageInput(media_id, open_id, content);

        for (String[] keys : equalKeyWordsMethod.keySet()) {
            for (String key : keys) {
                if (content.equals(key))
                    return equalKeyWordsMethod.get(keys).apply(packageInput);
            }
        }

        for (String[] keys : startWithKeyWordsMethod.keySet()) {
            for (String key : keys) {
                if (content.startsWith(key)) {
                    packageInput.content = packageInput.content.substring(key.length());

                    return startWithKeyWordsMethod.get(keys).apply(packageInput);
                }
            }
        }

        return turingRobotService.getResult(content, media_id, open_id, null);
    }

    /**
     * 事件解析的总方法
     *
     * @return
     */
    protected BaseMessage eventAnalysis(Map<String, String> parameter) {
        String eventType = parameter.get("Event");
        if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
            return getSubscribeMessage();
        } else {
            return new BaseMessage();
        }
    }

    /**
     * 订阅事件的返回值
     *
     * @return
     */
    protected BaseMessage getSubscribeMessage() {
        BaseMessage message = null;
        TextMessage textMessage = new TextMessage();
        textMessage.setContent(MessageUtil.SUBSCRIBE_MESSAGE);
        message = textMessage;
        message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        return message;
    }


    /**
     * 暂时无法识别的图片 语音等信息
     *
     * @param parameter
     * @return
     */
    protected TextMessage getErrorMessage(Map<String, String> parameter) {
        TextMessage textMessage = new TextMessage();
        textMessage.setContent("您于" + parameter.get("CreateTime") + "发送的"
                + new SimpleDateFormat("yyyy MM dd HH mm ss").format(new Date(Long.parseLong(parameter.get("CreateTime"))))
                + "消息已被后台接收，但暂时无法被识别");
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        return textMessage;
    }

    /**
     * 值班者模式
     *
     * @param mediaId
     * @param openId
     * @return
     */
    protected BaseMessage onduty(String mediaId, String openId) {
        Answerer answerer = DAOManager.answererDao.getAnswererByOpenId(openId);
        //不是回答者 直接return
        if (answerer == null) {
            TextMessage textMessage = new TextMessage();
            textMessage.setContent("抱歉，您尚无此权限。");
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            return textMessage;
        }
        //是回答者
        NewsMessage message = new NewsMessage();
        List<Article> articleList = new ArrayList<>();

        Article article = new Article();
        article.setTitle("今日尚未回答问题，值班老师请提醒未解答老师或转发问题。");
        article.setPicUrl(ImgUtil.NJU_QUESTION_BIG2);
        String code = ImplHelper.generateCodebyMid_Aid(mediaId, answerer.getAnswId() + "");
        article.setUrl(ServerUtil.SERVER_ADDRESS + "/core/onDuty?encrypt=" + code);

        articleList.add(article);

        message.setArticleCount(1);
        message.setArticles(articleList);
        message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

        return message;
    }

    /**
     * 学业咨询的关键字方法
     *
     * @param mediaId
     * @param openId
     * @return
     */
    protected BaseMessage inqueryStudy(String mediaId, String openId) {

        NewsMessage message = new NewsMessage();
        List<Article> articleList = new ArrayList<>();

        //判断属于那种人 questioner or answerer
        Answerer isAnswerer = DAOManager.answererDao.getAnswererByOpenId_MediaId(openId, mediaId);

        //最近回答列表
        Article article0 = new Article();
        article0.setTitle("最近公开提问及回答");
        article0.setPicUrl(ImgUtil.NJU_QUESTION_BIG1);
        String code0 = ImplHelper.generateCodebyMediaId(mediaId);
        article0.setUrl(ServerUtil.SERVER_ADDRESS + "/core/public_QA_list?encrypt=" + code0);
        articleList.add(article0);

        //最近回答列表
        Article article = new Article();
        article.setTitle("热门问答");
        article.setPicUrl(ImgUtil.KNOWLEDGEBASE);
        String code = ImplHelper.generateCodebyMediaId(mediaId);
        article.setUrl(ServerUtil.SERVER_ADDRESS + "/core/heatKnowledgeBase?encrypt=" + code);
        articleList.add(article);

        //如果是null说明不是回答者
        if (isAnswerer == null) {
            //我的回答列表
            Article article2 = new Article();
            article2.setTitle("我的提问");
            article2.setPicUrl(ImgUtil.MY_QUESTION);
            String code2 = ImplHelper.generateCodebyOpenId(openId);
            article2.setUrl(ServerUtil.SERVER_ADDRESS + "/core/my_question_list?encrypt=" + code2);

            //我要提问
            Article article3 = new Article();
            article3.setTitle("我要提问");
            article3.setDescription("未解决我的问题");
            article3.setPicUrl(ImgUtil.ASK_QUESTION_SMALL);
            String code3 = ImplHelper.generateCode(mediaId, openId);
            article3.setUrl(ServerUtil.SERVER_ADDRESS + "/core/ask_question?encrypt=" + code3);

            //articleList.add(article1);
            articleList.add(article2);
            articleList.add(article3);

        } else {
            //最近回答列表
            Article article1 = new Article();
            article1.setTitle("我的回答列表");
            article1.setPicUrl(ImgUtil.SOLVED_QUESTION);
            String code1 = ImplHelper.generateCodebyAnswererId(isAnswerer.getAnswId() + "");
            article1.setUrl(ServerUtil.SERVER_ADDRESS + "/core/my_answer_list?encrypt=" + code1);

            //我的回答列表
            Article article2 = new Article();
            article2.setTitle("我的未解决问题列表");
            article2.setPicUrl(ImgUtil.UNSOLVED_QUESTION);
            String code2 = ImplHelper.generateCodebyAnswererId(isAnswerer.getAnswId() + "");
            article2.setUrl(ServerUtil.SERVER_ADDRESS + "/core/unsolved_question_list?encrypt=" + code2);

            articleList.add(article1);
            articleList.add(article2);

        }

        // 设置图文消息个数
        message.setArticleCount(articleList.size());
        // 设置图文消息包含的图文集合
        message.setArticles(articleList);
        message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        return message;
    }

    /**
     * 讲座查询关键字的返回方法
     *
     * @param mediaId
     * @param openId
     * @return
     */
    protected BaseMessage inqueryLecture(String mediaId, String openId) {
        NewsMessage message = new NewsMessage();
        List<Article> articleList = new ArrayList<>();

        //全部讲座列表
        Article article0 = new Article();
        article0.setTitle("全部讲座列表");
        article0.setPicUrl(ImgUtil.ALLLECTURE_BIG);
        String code0 = ImplHelper.generateCode(mediaId, openId);
        article0.setUrl(ServerUtil.SERVER_ADDRESS + "/core/all_lectures?encrypt=" + code0);
        articleList.add(article0);

        //我报名的讲座
        Article article1 = new Article();
        article1.setTitle("我报名的讲座");
        article1.setPicUrl(ImgUtil.MYLECTURE);
        article1.setUrl(ServerUtil.SERVER_ADDRESS + "/core/joined_lectures?encrypt=" + code0);
        articleList.add(article1);

        //我感兴趣的讲座
        Article article2 = new Article();
        article2.setTitle("我感兴趣的讲座");
        article2.setPicUrl(ImgUtil.MYLIKES_LECTURE);
        article2.setUrl(ServerUtil.SERVER_ADDRESS + "/core/interest_lectures?encrypt=" + code0);
        articleList.add(article2);

        // 设置图文消息个数
        message.setArticleCount(articleList.size());
        // 设置图文消息包含的图文集合
        message.setArticles(articleList);
        message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        return message;
    }

    /**
     * 账号绑定的关键字方法
     *
     * @param keyword
     * @param FromUserName
     * @return
     */
    protected TextMessage validateKeyWord(String keyword, String FromUserName) {
        TextMessage message = new TextMessage();
        boolean correctKeyWord = this.answererService.checkValidateAnswerer(keyword, FromUserName);
        if (correctKeyWord) {
            message.setContent("账号绑定成功");
        } else {
            message.setContent("账号绑定失败，请检查您的密钥！");
        }
        message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        return message;
    }

    /**
     * 获得文档信息
     *
     * @return
     */
    protected BaseMessage getDocument() {
        TextMessage textMessage = new TextMessage();
        String content = DocumentHelper.getDocumentContent();
        textMessage.setContent(content);
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        return textMessage;
    }

    /**
     * 文本查询的关键字方法
     *
     * @param question
     * @param mediaId
     * @param openId
     * @return
     */
    protected BaseMessage getAnswer(String question, String mediaId, String openId) {

        NewsMessage message = new NewsMessage();

        List<ReQuestionVO> answers = this.searchQuestion.searchAnswerNum(question, DEFAULT_ANSWER_NUMBER);

        List<Article> articleList = new ArrayList<>(answers.size());
        for (ReQuestionVO answer : answers) {
            Article article = new Article();
            article.setTitle(answer.question);
            article.setDescription(answer.question);
            if (answer.questionImgUrl == ImgUtil.NJU_QUESTION_BIG1)
                article.setPicUrl(answer.questionImgUrl);
            article.setUrl(ServerUtil.SERVER_ADDRESS + answer.questionurl);

            articleList.add(article);
        }

        //判断如果未找到返回值
        if (answers.size() == 0) {
            //添加一个未解决问题的选项
            TextMessage text = new TextMessage();
            String code = ImplHelper.generateCode(mediaId, openId);
            text.setContent("不好意思，暂时没有找到您问题的答案，您可以点击<a href=\"" + ServerUtil.SERVER_ADDRESS + "/core/ask_question?encrypt=" + code + "\">提交问题</a>进行提问，您的问题将会有老师专门为您解答：）");
            text.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
            return text;
        }

        //添加一个未解决问题的选项
        Article article = new Article();
        article.setTitle("未解决我的问题");
        article.setDescription("未解决我的问题");
        article.setPicUrl(ImgUtil.ASK_QUESTION_SMALL);
        String code = ImplHelper.generateCode(mediaId, openId);
        article.setUrl(ServerUtil.SERVER_ADDRESS + "/core/ask_question?encrypt=" + code);

        articleList.add(article);

        // 设置图文消息个数
        message.setArticleCount(answers.size() + 1);
        // 设置图文消息包含的图文集合
        message.setArticles(articleList);
        message.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        return message;
    }
}

class PackageInput {
    public PackageInput(String media_id, String open_id, String content) {
        this.media_id = media_id;
        this.open_id = open_id;
        this.content = content;
    }

    public String media_id;
    public String open_id;
    public String content;
}