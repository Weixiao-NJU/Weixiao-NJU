package org.wx.weixiao.daoTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.dao.MediaInfoDAO;
import org.wx.weixiao.model.MediaInfo;

/**
 * MediaInfoDAO Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>12/31/2016</pre>
 */
public class MediaInfoDAOTest {
    MediaInfoDAO mediaInfoDAO;

    @Before
    public void before() throws Exception {
        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext
                ("applicationContext.xml");
        cxt.start();
        mediaInfoDAO = cxt.getBean(MediaInfoDAO.class);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getListByColumnValue(String colName, Object colValue)
     */
    @Test
    public void testGetListByColumnValue() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getByMediaId(String media_id)
     */
    @Test
    public void testGetByMediaId() throws Exception {
        MediaInfo mediaInfo=mediaInfoDAO.getByMediaId("gh_41594420b805");
        System.out.println(mediaInfo);
    }


} 
