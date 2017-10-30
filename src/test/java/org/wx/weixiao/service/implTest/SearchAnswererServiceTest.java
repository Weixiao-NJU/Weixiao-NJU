package org.wx.weixiao.service.implTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.Info.AnswererInfo;
import org.wx.weixiao.service.SearchAnswererService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry Wang on 2017/2/26.
 */
public class SearchAnswererServiceTest {

    private SearchAnswererService service;

    @Before
    public void setUp(){

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        this.service=(SearchAnswererService) context.getBean("SearchAnswererService");

    }
//    @Test
//    public void test() {
//        //service.deleteAnswerer(14+"");
//        AnswererInfo ans = new AnswererInfo();
//        ans.setId(13+"");
//        ans.setDepartment("教务处");
//        List<String> key = new ArrayList<>();
//        key.add("test1");
//        key.add("test3");
//        ans.setKeyword(key);
//        ans.setName("王家玮");
//        ans.setTelephone("123444");
//        ans.setAccountStr("xx");
//
//        System.out.println(service.updateAnswerer(ans));
//    }

//    @Test
//    public void testAdd(){
//        AnswererInfo ans = new AnswererInfo();
//        ans.setDepartment("教务处");
//        List<String> key = new ArrayList<>();
//        key.add("test1");
//        key.add("test3");
//        ans.setKeyword(key);
//        ans.setName("张三");
//        ans.setTelephone("123444");
//        ans.setAccountStr("xx");
//
//
//        System.out.println(service.addAnswerer(ans));
//    }

}
