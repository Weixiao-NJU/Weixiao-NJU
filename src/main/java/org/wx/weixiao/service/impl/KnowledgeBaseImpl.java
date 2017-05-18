package org.wx.weixiao.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Component;
import org.wx.weixiao.Info.KnowledgeInfo;
import org.wx.weixiao.common.LoggerManager;
import org.wx.weixiao.dao.DAOManager;
import org.wx.weixiao.model.KnowledgeBase;
import org.wx.weixiao.model.MediaInfo;
import org.wx.weixiao.service.KnowledgeBaseService;
import org.wx.weixiao.service.vo.ReQuestionVO;
import org.wx.weixiao.util.ErrorCodeUtil;
import org.wx.weixiao.util.ImgUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jerry Wang on 2016/12/20.
 */
@Component("KnowledgeBaseService")
public class KnowledgeBaseImpl implements KnowledgeBaseService {

    @Resource(name = "sessionFactory")
    protected SessionFactory sessionFactory;

    private static final String ANSWER_PAGE_URL = "/core/knowledgeBase?id=";

    private static final String[] NORMAL_KEYWORD = {"学分绩","第二专业","重修","退课"};

    private static Logger logger=Logger.getLogger(KnowledgeBaseImpl.class);

    @Override
    public int storeQuestion(KnowledgeInfo knowledgeInfo) {
        try {
            KnowledgeBase k = new KnowledgeBase();
            k.setQuestion(knowledgeInfo.getQuestion());
            k.setAnswer(knowledgeInfo.getAnswer());
            k.setIsDelete(0);
            k.setHeat(0L);
            MediaInfo m = DAOManager.mediaInfoDao.getByMediaId(knowledgeInfo.getMediaId());
            k.setMediaInfo(m);
            DAOManager.knowledgeBaseDao.save(k);
            LoggerManager.info(logger,"KnowledgeBase Added----Q:"+knowledgeInfo.getQuestion()+"----A:"+knowledgeInfo.getAnswer());
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorCodeUtil.CREATE_QUESTIONDOC_WRONG;
        }
        return ErrorCodeUtil.SUCCESS;
    }

    @Override
    public ReQuestionVO searchFirstAnswer(String question,int imageType) {
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        Transaction tx = fullTextSession.beginTransaction();

        // create native Lucene query unsing the query DSL
        // alternatively you can write the Lucene query using the Lucene query parser
        // or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(KnowledgeBase.class).get();
        org.apache.lucene.search.Query query = qb
                .keyword()
                .onFields("question","answer")
                .matching(question)
                .createQuery();

        // wrap Lucene query in a org.hibernate.Query
        org.hibernate.Query hibQuery =
                fullTextSession.createFullTextQuery(query, KnowledgeBase.class);

        // execute search
        List result = hibQuery.list();
        KnowledgeBase q = (KnowledgeBase) result.get(0);

        int id = q.getId();

        tx.commit();
        session.close();
        System.out.println();
        String imageUrl = "";
        if(imageType == 1)
            imageUrl = ImgUtil.NJU_QUESTION_BIG1;
        else
            imageUrl = ImgUtil.NJU_QUESTION_SMALL;

        return new ReQuestionVO(q.getQuestion(), ANSWER_PAGE_URL+id,imageUrl);

    }

    @Override
    public List<ReQuestionVO> searchNormalAnswer() {
        List<ReQuestionVO> re = new ArrayList<>();
        for(String keyword:NORMAL_KEYWORD){
            ReQuestionVO questionVO = null;
            if(keyword.equals(NORMAL_KEYWORD[0]))
                questionVO = searchFirstAnswer(keyword,1);
            else
                questionVO = searchFirstAnswer(keyword,0);
            re.add(questionVO);
        }
        return re;
    }

    @Override
    public KnowledgeInfo getKnowledgeBaseById(int id) {

        KnowledgeBase k = DAOManager.knowledgeBaseDao.get(id);
        KnowledgeInfo re = new KnowledgeInfo();
        if(k == null) {
            re.setId("0");
            re.setQuestion("暂无该问题内容");
            re.setAnswer("暂无该问题答案");
            return re;
        }
        if( k.getHeat() == null){
            k.setHeat(1L);
        }else{
            k.setHeat(k.getHeat()+1);
        }
        DAOManager.knowledgeBaseDao.saveOrUpdate(k);
        re.setQuestion(k.getQuestion());
        re.setAnswer(k.getAnswer());
        re.setId(k.getId()+"");
        return re;
    }

    @Override
    public List<ReQuestionVO> searchAnswerNum(String question, int number) {
        List<ReQuestionVO> reList = new ArrayList<ReQuestionVO>();
        if(number == 0)
            return reList;

        Session session = sessionFactory.openSession();
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
                .matching(question)
                .createQuery();

        // wrap Lucene query in a org.hibernate.Query
        org.hibernate.Query hibQuery =
                fullTextSession.createFullTextQuery(query, KnowledgeBase.class);

        // execute search
        List result = hibQuery.list();

        if(result.size()<number)
            number = result.size();

        for(int i = 0;i<number ; i++) {
            KnowledgeBase q = (KnowledgeBase) result.get(i);
            int id = q.getId();
            String image = ImgUtil.NJU_QUESTION_SMALL;
            if(i == 0)
                image = ImgUtil.NJU_QUESTION_BIG1;

            ReQuestionVO q1 = new ReQuestionVO(q.getQuestion(), ANSWER_PAGE_URL+id, image);

            reList.add(q1);
        }



        return reList;
    }

    @Override
    public List<KnowledgeInfo> getAllKnowledge(String mediaId) {
        List<KnowledgeBase> knowledgeBases = DAOManager.knowledgeBaseDao.getAllKnowledgeBase(mediaId);

        List<KnowledgeInfo> list=new ArrayList<KnowledgeInfo>();
        for(KnowledgeBase k:knowledgeBases){
            KnowledgeInfo a=new KnowledgeInfo(k.getId()+"",k.getQuestion(),k.getAnswer());
            list.add(a);
        }
        return list;
    }

    @Override
    public List<KnowledgeInfo> getHeatKnowledge(String mediaId) {
        return DAOManager.knowledgeBaseDao.getHeatKnowledgeBase(mediaId).stream().map(e->{
            KnowledgeInfo k = new KnowledgeInfo();
            k.setId(e.getId()+"");
            k.setMediaId(e.getMediaInfo().getMediaId());
            k.setQuestion(e.getQuestion());
            k.setAnswer(e.getAnswer());
            k.setHeat(e.getHeat()+"");
            return k;
        }).collect(Collectors.toList());
    }

    @Override
    public String getMediaIdbyAnswererId(String answererId) {
        return DAOManager.answererDao.get(Integer.parseInt(answererId)).getMediaInfo().getMediaId()+"";
    }

    @Override
    public int updateKnowledge(KnowledgeInfo knowledgeInfo) {
        try {
            KnowledgeBase k = DAOManager.knowledgeBaseDao.get(Integer.parseInt(knowledgeInfo.getId()));
            k.setQuestion(knowledgeInfo.getQuestion());
            k.setAnswer(knowledgeInfo.getAnswer());
            DAOManager.knowledgeBaseDao.update(k);

        }catch (NumberFormatException e){
            return ErrorCodeUtil.NUMBER_FORMAT_ERROR;
        }catch (Exception e) {
            return ErrorCodeUtil.UNKNOW_FAILURE;
        }
        return ErrorCodeUtil.SUCCESS;
    }

    @Override
    public int deleteKnowledge(String knowledgeID) {
       try {
           DAOManager.knowledgeBaseDao.fakeDeleteById(Integer.parseInt(knowledgeID));

       }catch (NumberFormatException e){
           return ErrorCodeUtil.NUMBER_FORMAT_ERROR;
       }catch (Exception e){
           return ErrorCodeUtil.UNKNOW_FAILURE;
       }
        return ErrorCodeUtil.SUCCESS;
    }
}
