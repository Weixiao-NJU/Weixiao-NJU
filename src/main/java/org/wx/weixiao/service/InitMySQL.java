package org.wx.weixiao.service;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wx.weixiao.dao.KnowledgeBaseDao;
import org.wx.weixiao.model.KnowledgeBase;

import java.io.File;
import java.util.List;

/**
 * Created by Jerry Wang on 2016/12/21.
 */
public class InitMySQL {

    private static ClassPathXmlApplicationContext context = null;
    private static SessionFactory sessionFactory;
    private static Session session;

    public static void initSession(){
        //默认加载hibernate.cfg.xml
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();

        sessionFactory = (SessionFactory) context.getBean("sessionFactory");
        session = sessionFactory.openSession();
    }

    public static void main(String args[]) throws DocumentException{
        initSession();
//        addXMLDatatoMySql();
        //setUpIndex();
        //searchTest();
        test();
    }

    public static void searchTest(){
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        Transaction tx = fullTextSession.beginTransaction();

        // create native Lucene query unsing the query DSL
        // alternatively you can write the Lucene query using the Lucene query parser
        // or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(KnowledgeBase.class).get();
        org.apache.lucene.search.Query query = qb
                .keyword()
                .onFields("question")
                .matching("学分绩")
                .createQuery();

        // wrap Lucene query in a org.hibernate.Query
        org.hibernate.Query hibQuery =
                fullTextSession.createFullTextQuery(query, KnowledgeBase.class);

        // execute search
        List result = hibQuery.list();
        //KnowledgeBase q = (KnowledgeBase) result.get(0);
        for(int i=0;i<result.size();i++) {
            KnowledgeBase q = (KnowledgeBase) result.get(i);
            System.out.println("question:"+q.getQuestion());
            System.out.println("answer  :"+q.getAnswer());
        }


        tx.commit();
        session.close();
    }

    public static void setUpIndex(){
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        try {
            fullTextSession.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void test(){
        KnowledgeBaseDao knowledgeBaseDao = context.getBean(KnowledgeBaseDao.class);
        knowledgeBaseDao.get(0).getQuestion();
    }

    public static void addXMLDatatoMySql()throws DocumentException{
        SAXReader reader = new SAXReader();
        Document document = reader.read(InitMySQL.class.getResourceAsStream("/KnowledgeBase.xml"));
        Element root = document.getRootElement();
        List<Element> childElements = root.elements();
        //加载dao
        KnowledgeBaseDao knowledgeBaseDao = context.getBean(KnowledgeBaseDao.class);


        for (Element child : childElements) {

            //打印出当前文件里的知识库列表
            System.out.println("question: " + child.elementText("question"));
            System.out.println("answer  : " + child.elementText("answer"));
            System.out.println();
            String question = child.elementText("question");
            String answer = child.elementText("answer");

            KnowledgeBase k = new KnowledgeBase(question,answer);
            k.setIsDelete(0);

            knowledgeBaseDao.save(k);


        }
    }

}
