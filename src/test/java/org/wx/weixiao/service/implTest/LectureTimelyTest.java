package org.wx.weixiao.service.implTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.wx.weixiao.service.LectureTimerService;

/**
 * Created by darxan on 2017/4/27.
 */
@Component("CheckLectureTimerService")
public class LectureTimelyTest {
    private LectureTimerService service;

    @Before
    public void setUp() {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        this.service = (LectureTimerService) context.getBean("LectureTimerService");

    }


    @Test
    public void testSave() {
        service.updateLectures();
    }

    public void testEmail() {

        service.emailSubscribers(24*60*60*1000);
    }
}
