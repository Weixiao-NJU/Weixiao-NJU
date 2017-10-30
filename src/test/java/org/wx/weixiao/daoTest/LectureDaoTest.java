package org.wx.weixiao.daoTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.dao.LectureDao;

/**
 * LectureDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>05/02/2017</pre>
 */
public class LectureDaoTest {
    LectureDao lectureDao;

    @Before
    public void before() throws Exception {
        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext
                ("applicationContext.xml");
        cxt.start();
        lectureDao = cxt.getBean(LectureDao.class);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getShouldEmailsLecture(Date start, Date before)
     */
    @Test
    public void testGetShouldEmailsLecture() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: newestLectureId()
     */
    @Test
    public void testNewestLectureId() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getListByColumnValue(String colName, Object colValue)
     */
    @Test
    public void testGetListByColumnValue() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getLectureById(int id)
     */
    @Test
    public void testGetLectureById() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: fetchHistoryList()
     */
    @Test
    public void testFetchHistoryList() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: fetchFutureList()
     */
    @Test
    public void testFetchFutureList() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getTotalRecordNum()
     */
    @Test
    public void testGetTotalRecordNum() throws Exception {
//TODO: Test goes here... 
    }

//    /**
//     * Method: interest(int lid)
//     */
//    @Test
//    public void testInterest() throws Exception {
//        List<Lecture> list=lectureDao.getListByColumnValue("lid",1);
//        if(!list.isEmpty()){
//            Lecture l=list.get(0);
//            l.setInterestNum(l.getInterestNum()+1);
//            System.out.println(l.getTitle()+","+l.getInterestNum());
//            lectureDao.saveOrUpdate(l);
//        }
//    }


} 
