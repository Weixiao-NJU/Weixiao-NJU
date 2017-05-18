package org.wx.weixiao.service.implTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.service.impl.ImportAnswererImpl;

/**
 * ImportAnswererImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>01/01/2017</pre>
 */
public class ImportAnswererImplTest {

    ImportAnswererImpl importAnswerer;

    @Before
    public void before() throws Exception {
        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext
                ("applicationContext.xml");
        cxt.start();
        importAnswerer = cxt.getBean(ImportAnswererImpl.class);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: importAnswererInfo(String file_dir)
     */
    @Test
    public void testImportAnswererInfo() throws Exception {
        importAnswerer.importAnswererInfo("D:/NJU.xlsx");
    }


} 
