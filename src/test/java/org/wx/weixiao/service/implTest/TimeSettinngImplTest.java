package org.wx.weixiao.service.implTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.service.impl.TimeSettinngImpl;

/**
 * TimeSettinngImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>03/15/2017</pre>
 */
public class TimeSettinngImplTest {
    TimeSettinngImpl timeSettinng;

    @Before
    public void before() throws Exception {
        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext
                ("applicationContext.xml");
        cxt.start();
        timeSettinng = cxt.getBean(TimeSettinngImpl.class);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: setTime(String startTime, String endTime, String mediaId)
     */
    @Test
    public void testSetTime() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: isBetweenTime(String mediaId)
     */
    @Test
    public void testIsBetweenTime() throws Exception {
        System.out.println(timeSettinng.isBetweenTime("gh_41594420b805"));
    }

    /**
     * Method: readStartTime(String mediaId)
     */
    @Test
    public void testReadStartTime() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: readEndTime(String mediaId)
     */
    @Test
    public void testReadEndTime() throws Exception {
//TODO: Test goes here... 
    }


} 
