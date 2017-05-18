package org.wx.weixiao.dao;

import org.springframework.context.ApplicationContext;
import org.wx.weixiao.model.ConstParameters;
import org.wx.weixiao.model.LectureSubscriber;
import org.wx.weixiao.util.ApplicationContextHelper;

/**
 * Created by Jerry Wang on 2016/12/30.
 */
public class DAOManager {

    public final static AnswererDao answererDao;

    public final static AnswerDao answerDao;

    public final static DepartmentDao departmentDao;

    public final static KeywordAnswererDao keywordAnswererDao;

    public final static KnowledgeBaseDao knowledgeBaseDao;

    public final static MediaInfoDAO mediaInfoDao;

    public final static QuestionDao questionDao;

    public final static QuestionerDao questionerDao;

    public final static ConstParametersDao constParameterDao;

    public final static LectureDao lectureDao;

    public final static LectureSubscriberDao lectureSubscriberDao;

    static {
        ApplicationContext context = ApplicationContextHelper.getApplicationContext();
        answererDao = context.getBean(AnswererDao.class);
        departmentDao = context.getBean(DepartmentDao.class);
        keywordAnswererDao = context.getBean(KeywordAnswererDao.class);
        knowledgeBaseDao = context.getBean(KnowledgeBaseDao.class);
        mediaInfoDao = context.getBean(MediaInfoDAO.class);
        questionDao = context.getBean(QuestionDao.class);
        questionerDao = context.getBean(QuestionerDao.class);
        answerDao = context.getBean(AnswerDao.class);
        constParameterDao=context.getBean(ConstParametersDao.class);
        lectureDao=context.getBean(LectureDao.class);
        lectureSubscriberDao=context.getBean(LectureSubscriberDao.class);
    }

}
