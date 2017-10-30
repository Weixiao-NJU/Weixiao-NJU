package org.wx.weixiao.daoTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.dao.ConstParametersDao;
import org.wx.weixiao.dao.MediaInfoDAO;

/**
 * ConstParametersDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>02/24/2017</pre>
 */
public class ConstParametersDaoTest {
    ConstParametersDao constParametersDao;
    MediaInfoDAO mediaInfoDAO;

    @Before
    public void before() throws Exception {
        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext
                ("applicationContext.xml");
        cxt.start();
        constParametersDao = cxt.getBean(ConstParametersDao.class);
        mediaInfoDAO = cxt.getBean(MediaInfoDAO.class);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: setTime(String startTime, String endTime, String mediaId)
     */
//    @Test
//    public void testSetTime() throws Exception {
//        ConstParameters constParameters = new ConstParameters();
//        constParameters.setMediaInfo(mediaInfoDAO.get(1));
//        constParameters.setWork_time_start("07:00");
//        constParameters.setWork_time_end("08:00");
//        constParametersDao.save(constParameters);
//    }

    /**
     * Method: readParameters(String mediaId)
     */
    @Test
    public void testReadParameters() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: readStartTime(int mediaId)
     */
    @Test
    public void testReadStartTime() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: readEndTime(int mediaId)
     */
    @Test
    public void testReadEndTime() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getConstParameters(int mediaId)
     */
    @Test
    public void testGetConstParameters() throws Exception {
//TODO: Test goes here... 
    }


} 
