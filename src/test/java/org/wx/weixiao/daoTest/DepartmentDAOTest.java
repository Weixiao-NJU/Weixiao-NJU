package org.wx.weixiao.daoTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.dao.DepartmentDao;

/**
 * Created by Jerry Wang on 2017/2/26.
 */
public class DepartmentDAOTest {

    DepartmentDao departmentDao;

    @Before
    public void before() throws Exception {
        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext
                ("applicationContext.xml");
        cxt.start();
        departmentDao = cxt.getBean(DepartmentDao.class);
    }

    @Test
    public void test(){
        System.out.println(departmentDao.getDepartmentByName("教务处"));
    }

}
