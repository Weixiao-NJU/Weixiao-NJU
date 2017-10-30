package org.wx.weixiao.service.implTest;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.service.KnowledgeBaseService;
import org.wx.weixiao.service.impl.KnowledgeBaseImpl;
import org.wx.weixiao.service.vo.ReQuestionVO;

import java.util.List;

/**
 * Created by Jerry Wang on 2016/12/17.
 */
public class KnowledgeBaseTest {

    private KnowledgeBaseService knowledgeBaseService;
    ClassPathXmlApplicationContext cxt;
    @Before
    public void setUp(){
        cxt = new ClassPathXmlApplicationContext
                ("applicationContext.xml");
        cxt.start();
        knowledgeBaseService = cxt.getBean(KnowledgeBaseImpl.class);
    }

    //@Test
//    public void testIndex(){
//                SessionFactory sessionFactory = (SessionFactory) cxt.getBean("sessionFactory");
//               FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.openSession());
//        try {
//            fullTextSession.createIndexer().startAndWait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//    }

    @Test
    public void testSearch(){
        List<ReQuestionVO> re = knowledgeBaseService.searchAnswerNum("是不是只能这样啊",10);
        re.forEach(e->System.out.println(new Gson().toJson(e)));
        //System.out.println(new Gson().toJson(re));
    }
//

//    @Test
//    public void addKnowledge(){
//        KnowledgeInfo k = new KnowledgeInfo();
//
//        k.setQuestion("测试一下子");
//        k.setAnswer("真厉害");
//        k.setMediaId("1");
//        knowledgeBaseService.storeQuestion(k);
//    }

//    @Test
//    public void testUpdate(){
//        KnowledgeInfo k = new KnowledgeInfo();
//        k.setMediaId("1");
//        k.setQuestion("testttt");
//        k.setAnswer("testttt");
//        k.setId("75");
//        knowledgeBaseService.updateKnowledge(k);
//    }
 //   @Test
   // public void testDelete(){
        //System.out.println(knowledgeBaseService.deleteKnowledge(62+""));
        //System.out.println(DAOManager.knowledgeBaseDao.get(62).getIsDelete());
        //KnowledgeInfo k = new KnowledgeInfo();
        //k.setId(62+"");
        //k.setQuestion("2313");
        //k.setAnswer("hahaaaaaa");
        //k.setMediaId("1");
       // knowledgeBaseService.updateKnowledge(k);
 //   }

}
