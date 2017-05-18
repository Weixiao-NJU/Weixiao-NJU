package org.wx.weixiao.service.implTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.message.resp.BaseMessage;
import org.wx.weixiao.service.AllDispatchCoreService;

import java.util.HashMap;
import java.util.Map;

import static org.wx.weixiao.util.MessageUtil.REQ_MESSAGE_TYPE_TEXT;

/**
 * AllDispatchCoreImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>04/30/2017</pre>
 */
public class AllDispatchCoreImplTest {
    AllDispatchCoreService allDispatchCoreService;
    Map<String, String> parameters;

    @Before
    public void before() throws Exception {
        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext
                ("applicationContext.xml");
        cxt.start();
        allDispatchCoreService = cxt.getBean(AllDispatchCoreService.class);

        parameters=new HashMap<>();
        parameters.put("FromUserName","fromuser");
        parameters.put("ToUserName","touser");
        parameters.put("Content","帮助");
        parameters.put("MsgType",REQ_MESSAGE_TYPE_TEXT);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: handleRequest(HttpServletRequest request)
     */
    @Test
    public void testHandleRequest() throws Exception {
    }

    /**
     * Method: handleInput(Map<String, String> parameters)
     */
    @Test
    public void testHandleInput() throws Exception {
        String respMess=allDispatchCoreService.handleInput(parameters);
        System.out.println(respMess);
    }

    /**
     * Method: textAnalysis(Map<String, String> parameters)
     */
    @Test
    public void testTextAnalysis() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: eventAnalysis(Map<String, String> parameter)
     */
    @Test
    public void testEventAnalysis() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getSubscribeMessage()
     */
    @Test
    public void testGetSubscribeMessage() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getErrorMessage(Map<String, String> parameter)
     */
    @Test
    public void testGetErrorMessage() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: onduty(String mediaId, String openId)
     */
    @Test
    public void testOnduty() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: inqueryStudy(String mediaId, String openId)
     */
    @Test
    public void testInqueryStudy() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: validateKeyWord(String keyword, String FromUserName)
     */
    @Test
    public void testValidateKeyWord() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getAnswer(String question, String mediaId, String openId)
     */
    @Test
    public void testGetAnswer() throws Exception {
//TODO: Test goes here... 
    }


} 
