package org.wx.weixiao.service.implTest;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.message.resp.BaseMessage;
import org.wx.weixiao.service.TuringRobotService;

/**
 * TuringRobotServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>04/26/2017</pre>
 */
public class TuringRobotServiceImplTest {
    TuringRobotService turingRobotService;

    @Before
    public void before() throws Exception {
        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext
                ("applicationContext.xml");
        cxt.start();
        turingRobotService = cxt.getBean(TuringRobotService.class);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getResult(String info, String mediaId, String userId, String loc)
     */
    @Test
    public void testGetResult() throws Exception {
        BaseMessage baseMessage = turingRobotService.getResult("学分绩", "", "001", null);
        System.out.println(new Gson().toJson(baseMessage));
    }

    /**
     * Method: apply(JsonObject jsonObject)
     */
    @Test
    public void testApplyJsonObject() throws Exception {
    }


} 
