package org.wx.weixiao.service.implTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.service.UploadService;

/**
 * Created by lizhimu on 2017/1/10.
 */
public class UploadServiceTest {
    private UploadService service;
    @Before
    public void setUp(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        this.service=(UploadService) context.getBean("UploadService");
    }

    @Test
    public void test(){

    }

//    private CheckAnswererService service;
//
//    @Before
//    public void setUp(){
//
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        context.start();
//        this.service=(CheckAnswererService) context.getBean("CheckAnswerService");
//
//    }
//    @Test
//    public void test() {
//
//        boolean b=service.checkValidateAnswerer("feifeifei","6afd");
//        System.out.println(b);
//    }

}
