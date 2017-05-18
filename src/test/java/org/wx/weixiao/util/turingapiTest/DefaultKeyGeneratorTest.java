package org.wx.weixiao.util.turingapiTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wx.weixiao.service.impl.DefaultTuringKeyGenerator;
import org.wx.weixiao.util.turingapi.TuringKeyGenerator;

/**
 * DefaultTuringKeyGenerator Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>04/26/2017</pre>
 */
public class DefaultKeyGeneratorTest {
    TuringKeyGenerator defaultKeyGenerator;

    @Before
    public void before() throws Exception {
        defaultKeyGenerator = new DefaultTuringKeyGenerator();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getKey()
     */
    @Test
    public void testGetKey() throws Exception {
        System.out.println(defaultKeyGenerator.getKey(""));
    }


} 
