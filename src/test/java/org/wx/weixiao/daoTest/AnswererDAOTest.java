package org.wx.weixiao.daoTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.dao.AnswererDao;
import org.wx.weixiao.model.Answerer;

/**
 * Created by Jerry Wang on 2017/1/15.
 */
public class AnswererDAOTest {
    AnswererDao answererDao;


    @Before
    public void before() throws Exception {
        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext
                ("applicationContext.xml");
        cxt.start();
        answererDao = cxt.getBean(AnswererDao.class);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getNoAnswerQuestions()
     */
    @Test
    public void test() throws Exception {
        Answerer answerer = answererDao.get(12);
        answererDao.fakeDelete(answerer);
    }
}
