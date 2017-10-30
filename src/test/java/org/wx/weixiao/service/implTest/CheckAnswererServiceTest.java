package org.wx.weixiao.service.implTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.service.CheckAnswererService;

/**
 * Created by lizhimu on 2016/12/28.
 */
public class CheckAnswererServiceTest {
    private CheckAnswererService service;

    @Before
    public void setUp(){

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        this.service=(CheckAnswererService) context.getBean("CheckAnswerService");

    }
    @Test
    public void test() {

        boolean b=service.checkValidateAnswerer("feifeifei","6afd");
        System.out.println(b);
    }


}
