package org.wx.weixiao.service.impl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wx.weixiao.Info.AnswerInfo;
import org.wx.weixiao.Info.AnswererInfo;
import org.wx.weixiao.Info.QuestionInfo;
import org.wx.weixiao.dao.DAOManager;
import org.wx.weixiao.message.weixiao.CustomMessage;
import org.wx.weixiao.message.weixiao.CustomMessageNewsData;
import org.wx.weixiao.message.weixiao.NewsContent;
import org.wx.weixiao.model.*;
import org.wx.weixiao.service.QuestionAndAnswerService;
import org.wx.weixiao.service.TimeSettingService;
import org.wx.weixiao.servlet.conf.AppConfig;
import org.wx.weixiao.util.AppConfigUtil;
import org.wx.weixiao.util.ErrorCodeUtil;
import org.wx.weixiao.util.ServerUtil;
import org.wx.weixiao.util.weixiaoapi.CustomMessageAPI;
import org.wx.weixiao.util.weixiaoapi.UserInfoAPI;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zs14 on 2016/12/28.
 */
@Component("QuestionAndAnswerService")
public class QuestionAndAnswerImpl implements QuestionAndAnswerService {
    @Autowired
    TimeSettingService timeSettingService;

    @Override
    public int addQuestion(QuestionInfo info) {
        //添加问题
        //TODO 提问者详细信息的添加

        Question q = new Question();
        q.setContent(info.getContent());
        q.setIsDelete(0);
        q.setIsAnswered(0);
        q.setKeyword(DAOManager.keywordAnswererDao.getKeyWordBykeyword(info.getKeyword()));
        q.setCreateAt(new Timestamp(System.currentTimeMillis()));
        //设置问题所属公众号
        MediaInfo m = DAOManager.mediaInfoDao.getByMediaId(info.getQuestion_media_id());
        q.setMediaInfo(m);
        //设置问题所属提问者
        Questioner questioner = DAOManager.questionerDao.getQuestionerbyOpenId(info.getQuestioner_open_id());
        if (questioner == null) {
            questioner = new Questioner();
            questioner.setOpen_id(info.getQuestioner_open_id());
            String name = UserInfoAPI.getUserInfo(questioner.getOpen_id()).nickname;
            if (name != null) {
                try {
                    name = URLEncoder.encode(name, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    name = null;
                }
            }
            questioner.setName(name);
            questioner.setIsDelete(0);
            DAOManager.questionerDao.saveOrUpdate(questioner);
        }
        q.setQuestioner(questioner);

        if (timeSettingService.isBetweenTime(info.getQuestion_media_id())) {
            q.setDispatched(true);
            DAOManager.questionDao.saveOrUpdate(q);
            dispatch(q);
            return ErrorCodeUtil.SUCCESS;
        } else {
            q.setDispatched(false);
            DAOManager.questionDao.saveOrUpdate(q);
            return ErrorCodeUtil.QUESTION_NOT_DISPATCHED;
        }


    }

    @Override
    public int addAnswer(AnswerInfo info) {

        //获取question
        Question question = DAOManager.questionDao.get(Integer.parseInt(info.getQuestionId()));
        question.setIsAnswered(1);
        question.setDispatched(true);
        question.setIsOpen(info.isOpen() ? 1 : 0);
        
        //获取answerer
        Answerer answerer = DAOManager.answererDao.get(Integer.parseInt(info.getAnswererId()));

        //创建answer
        Answer answer = new Answer();
        answer.setContent(info.getContent());
        answer.setIsDelete(0);
        answer.setCreateAt(new Timestamp(System.currentTimeMillis()));
        answer.setQuestion(question);
        answer.setAnswerer(answerer);
        //保存answer
        DAOManager.answerDao.save(answer);

        //发送给提问者
        AppConfig appConfig = AppConfigUtil.get(AppConfigUtil.ALLDISPATCH);

        //封装发送数据
        CustomMessage customMessage = new CustomMessage();
        customMessage.setOpenid(question.getQuestioner().getOpen_id());
        customMessage.setMedia_id(question.getMediaInfo().getMediaId());

        //封装数据发送
        CustomMessageNewsData customMessageData = new CustomMessageNewsData();
        List<NewsContent> re = new ArrayList<>();
        NewsContent newsContent = new NewsContent();
        newsContent.setUrl(ServerUtil.SERVER_ADDRESS + "/core/get_reply?encrypt=" + ImplHelper.generateCodebyQuestionId(question.getQuestionId() + ""));
        newsContent.setTitle("您提交的疑问已被回答");
        newsContent.setDescription("您于" + question.getCreateAt() + "的问题\"" + question.getContent() +
                "\"已经被" + answerer.getDepartment().getName() + answerer.getName() + "老师回答。");
        re.add(newsContent);
        customMessageData.setNews(re);

        customMessage.setData(customMessageData);

        //发送消息
        CustomMessageAPI.sendCustomMessage(customMessage, appConfig);
        return ErrorCodeUtil.SUCCESS;
    }


    public int dispatch(Question q) {
        //获取问题发送人
        Answerer ans = q.getKeyword().getAnswerer();
        String answererOpenId = ans.getOpenId();

        AppConfig appConfig = AppConfigUtil.get(AppConfigUtil.ALLDISPATCH);

        CustomMessage customMessage = new CustomMessage();
        customMessage.setApi_key(appConfig.getApiKey());

        CustomMessageNewsData customMessageNewsData = new CustomMessageNewsData();
        NewsContent newsContent = new NewsContent();
        newsContent.setTitle("您有新的学生提问：）");
        newsContent.setDescription(q.getContent());
        String code = ImplHelper.generateCodebyQid_Aid(q.getQuestionId() + "", ans.getAnswId() + "");
        newsContent.setUrl(ServerUtil.SERVER_ADDRESS + String.format
                ("/core/answer_question?encrypt=%s", code));


        customMessageNewsData.setNews(Arrays.asList(newsContent));

        customMessage.setData(customMessageNewsData);
        customMessage.setMedia_id(ans.getMediaInfo().getMediaId());
        customMessage.setOpenid(answererOpenId);
        customMessage.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));

        System.out.println(new Gson().toJson(CustomMessageAPI.sendCustomMessage(customMessage,
                appConfig)));

        return ErrorCodeUtil.SUCCESS;
    }

    @Override
    public QuestionInfo searchQuestion(String questionId) {
        QuestionInfo re = new QuestionInfo();
        try {
            Question q = DAOManager.questionDao.get(Integer.parseInt(questionId));
            re.setIsAnswered(q.getIsAnswered());
            re.setId(questionId);
            re.setContent(q.getContent());
            re.setCreateAt(q.getCreateAt().toString());
            re.setQuestioner_open_id(q.getQuestioner().getOpen_id());
            re.setKeyword(q.getKeyword().getKeyword());
            //TODO createUser暂时使用公众号的接口
            String name = q.getQuestioner().getName();
            if (name != null) {
                name = URLDecoder.decode(name, "utf-8");
            } else {
                Questioner questioner = q.getQuestioner();
                String nickName = UserInfoAPI.getUserInfo(questioner.getOpen_id()).nickname;
                if (nickName == null)
                    name = "Unknown";
                else {
                    questioner.setName(nickName);
                    DAOManager.questionerDao.saveOrUpdate(questioner);
                }
            }
            re.setCreateUser(name);
        } catch (Exception e) {
            return null;
        }
        return re;
    }


    @Override
    public List<AnswerInfo> getAnswers(String questionId) {

        List<Answer> reAnswer = DAOManager.answerDao.getAnswersByQuesionId(questionId);

        System.out.println(reAnswer.size() + "---------------------------------");

        return reAnswer.stream().map(e -> {
            AnswerInfo answerInfo = new AnswerInfo(questionId, e.getContent(), e.getAnswerer().getAnswId() + "", e.getAnswerer().getName(), e.getCreateAt().toString());
            return answerInfo;
        }).collect(Collectors.toList());

    }


    @Override
    public int dispatchQuestion(AnswererInfo fromAnswerer, AnswererInfo toAnswerer, String questionId) {
        //获取问题发送人
        //System.out.println("hahaha:"+ fromAnswerer.getId()+ ",, "+ toAnswerer.getId()+";;;");
        Answerer ans = DAOManager.answererDao.get(Integer.parseInt(toAnswerer.getId()));

        String answererOpenId = ans.getOpenId();

        //获取问题
        Question q = DAOManager.questionDao.get(Integer.parseInt(questionId));
        q.setKeyword(ans.getKeywordAnswer().get(0));

        DAOManager.questionDao.update(q);

        AppConfig appConfig = AppConfigUtil.get(AppConfigUtil.ALLDISPATCH);

        CustomMessage customMessage = new CustomMessage();
        customMessage.setMedia_id(ans.getMediaInfo().getMediaId());
        customMessage.setOpenid(answererOpenId);

        //customMessage.setApi_key(appConfig.getApiKey());

        CustomMessageNewsData customMessageNewsData = new CustomMessageNewsData();
        NewsContent newsContent = new NewsContent();
        newsContent.setTitle("您有被" + fromAnswerer.getName() + "老师转发的的学生提问：）");
        newsContent.setDescription(q.getContent());
        String code = ImplHelper.generateCodebyQid_Aid(q.getQuestionId() + "", ans.getAnswId() + "");
        newsContent.setUrl(ServerUtil.SERVER_ADDRESS + String.format
                ("/core/answer_question?encrypt=%s", code));


        customMessageNewsData.setNews(Arrays.asList(newsContent));

        customMessage.setData(customMessageNewsData);

        customMessage.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));

        System.out.println(new Gson().toJson(
                CustomMessageAPI.sendCustomMessage(customMessage, appConfig)
        ));

        return ErrorCodeUtil.SUCCESS;
    }

    @Override
    public void dispatchEveryDay(String mediaId) {
        DAOManager.questionDao.getUndispatchedQuestion(mediaId).forEach
                (this::dispatch);
    }

}
