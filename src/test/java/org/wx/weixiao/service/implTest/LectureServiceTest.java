package org.wx.weixiao.service.implTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.wx.weixiao.Info.LectureInfo;
import org.wx.weixiao.service.LectureService;

import java.util.List;

/**
 * Created by lizhimu on 2017/4/26.
 */
@Component("CheckLectureService")
public class LectureServiceTest {

    private LectureService service;

    @Before
    public void setUp() {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        this.service = (LectureService) context.getBean("LectureService");

    }

//    @Test
//    public void testLectureList() {
//        System.out.println("Lecture list test");
//        List<LectureInfo> list = service.lectureList(true, 1, 10);
//        for (LectureInfo l : list) {
//            System.out.println(l);
//            System.out.println(l.getStartTime());
//        }
//        System.out.println("---------------------------");
//        List<LectureInfo> list1 = service.lectureList(false, 3, 10);
//        for (LectureInfo l : list) {
//            System.out.println(l);
//            System.out.println(l.getStartTime());
//        }
//    }

//    @Test
//    public void testGetTotalPage() {
//        int pageNum = service.getTotalPage(true,10);
//        System.out.println("total page is: " + pageNum);
//
//        int currentNum = service.getTotalPage(false,10);
//        System.out.println("current page is: " + currentNum);
//    }

//    @Test
//    public void testLecture() {
//        LectureInfo l = service.lecture(2);
//        System.out.println(l);
//    }

//    @Test
//    public void testGetSubscribe() {
//
//        List<LectureSubscriberInfo> infoList = service.getSubscribers(2);
//        for (LectureSubscriberInfo info : infoList) {
//            System.out.println(info);
//        }
//    }

//    @Test
//    public void testInterest() {
//        service.interest("124",2);
//        LectureInfo l = service.lecture(1752);
//        System.out.println(l.getInterestNum());
//    }

//    @Test
//    public void testSubscribe() {
//        LectureSubscriberInfo info=new LectureSubscriberInfo();
//        info.setPhone("123456789");
//        info.setEmail("12345@qq.com");
//        info.setSubscriber_id(1);
//        info.setLectureLId(1752);
//        info.setOpenid("2325");
//        service.subscribe(info);
//
//    }

//    @Test
//    public void testIsSubscriber(){
//        boolean b1=service.isSubscriber("234",2);
//        System.out.println(b1);
//        boolean b2=service.isSubscriber("235",2);
//        System.out.println(b2);
//    }

//    @Test
//    public void testIsInterester(){
//        boolean b1=service.isInterest("234",2);
//        System.out.println(b1);
//        boolean b2=service.isInterest("235",2);
//        System.out.println(b2);
//    }

//    @Test
//    public void testGetInterestList(){
//        List<LectureInfo> lectureInfoList=service.getInterestList("124");
//        for(LectureInfo l:lectureInfoList){
//            System.out.println(l.getLid());
//        }
//    }
    @Test
    public void testGetSubscribeList(){
        List<LectureInfo> lectureInfoList=service.getSubscriberList("123");
        for(LectureInfo l:lectureInfoList){
            System.out.println(l.getLid());
        }
    }




}
