package org.wx.weixiao.service.implTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.Info.AnswerInfo;
import org.wx.weixiao.Info.AnswererInfo;
import org.wx.weixiao.service.QuestionAndAnswerService;
import org.wx.weixiao.service.impl.QuestionAndAnswerImpl;

import java.util.List;

/**
 * QuestionAndAnswerImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>01/03/2017</pre>
 */
public class QuestionAndAnswerImplTest {
    QuestionAndAnswerService questionAndAnswerService;

    @Before
    public void before() throws Exception {
        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext
                ("applicationContext.xml");
        cxt.start();
        questionAndAnswerService = cxt.getBean(QuestionAndAnswerImpl.class);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: addQuestion(QuestionInfo info)
     */
    @Test
    public void testAddQuestion() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: addAnswer(AnswerInfo info)
     */
    @Test
    public void testAddAnswer() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: dispatchQuestion(Question q)
     */
    @Test
    public void testDispatchQuestion() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: searchQuestion(String questionId)
     */
    @Test
    public void testSearchQuestion() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getAnswers(String questionId)
     */
    @Test
    public void testGetAnswers() throws Exception {
        List<AnswerInfo> answererInfos=questionAndAnswerService.getAnswers("2");
        System.out.println(answererInfos.size());
    }


} 
