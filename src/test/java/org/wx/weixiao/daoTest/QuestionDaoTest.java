package org.wx.weixiao.daoTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.dao.QuestionDao;
import org.wx.weixiao.dao.QuestionerDao;
import org.wx.weixiao.model.Questioner;
import org.wx.weixiao.util.weixiaoapi.UserInfoAPI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * QuestionDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>01/03/2017</pre>
 */
public class QuestionDaoTest {
    QuestionDao questionDao;
    QuestionerDao questionerDao;


    @Before
    public void before() throws Exception {
        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext
                ("applicationContext.xml");
        cxt.start();
        questionDao = cxt.getBean(QuestionDao.class);
        questionerDao = cxt.getBean(QuestionerDao.class);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getNoAnswerQuestions()
     */
    @Test
    public void testGetNoAnswerQuestions() throws Exception {
        List<Questioner> questions = questionerDao.getListByHQL("from Questioner q");
        questions.stream().forEach(e -> {
            try {
                String name = e.getName();
//                if (name != null) {
//                    System.out.println(URLDecoder.decode(name, "utf-8"));
//                } else {
                String nickname = UserInfoAPI.getUserInfo(e.getOpen_id()).nickname;
                if (nickname != null) {
                    e.setName(URLEncoder.encode(UserInfoAPI.getUserInfo(e.getOpen_id()).nickname, "utf-8"));
                    questionerDao.saveOrUpdate(e);
//                    }
                }
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        });
    }

    @Test
    public void testGetUnDispatchQuestions() throws Exception {
        questionDao.getUndispatchedQuestion("gh_41594420b805").forEach(e -> {
            System.out.println(e.getContent());
        });
    }


} 
