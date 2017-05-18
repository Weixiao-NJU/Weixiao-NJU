package org.wx.weixiao.utilTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wx.weixiao.util.DocumentHelper;

/**
 * DocumentHelper Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>05/05/2017</pre>
 */
public class DocumentHelperTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getDocumentContent()
     */
    @Test
    public void testGetDocumentContent() throws Exception {
        String document = DocumentHelper.getDocumentContent();
        System.out.println(document);
    }


} 
