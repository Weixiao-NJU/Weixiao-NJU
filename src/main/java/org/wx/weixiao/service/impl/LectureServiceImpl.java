package org.wx.weixiao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wx.weixiao.Info.LectureInfo;
import org.wx.weixiao.Info.LectureSubscriberInfo;
import org.wx.weixiao.dao.LectureDao;
import org.wx.weixiao.dao.LectureInteresterDao;
import org.wx.weixiao.dao.LectureSubscriberDao;
import org.wx.weixiao.model.Lecture;
import org.wx.weixiao.model.LectureInterester;
import org.wx.weixiao.model.LectureSubscriber;
import org.wx.weixiao.service.LectureService;

import java.util.*;

/**
 * Created by lizhimu on 2017/4/26.
 */
@Component("LectureService")
public class LectureServiceImpl implements LectureService{
    @Autowired
    private LectureDao lectureDao;
    @Autowired
    private LectureInteresterDao lectureInteresterDao;

    @Autowired
    private LectureSubscriberDao lectureSubscriberDao;
    @Override
    public List<LectureInfo> lectureList(boolean all, int page, int pageSize) {
        if(pageSize<1){
            return lectureList(all,page,10);
        }

        List<Lecture> lectureList= lectureDao.fetchFutureList();
        if(all){
            lectureList.addAll(lectureDao.fetchHistoryList());
        }

        List<LectureInfo> lectureInfoList=new ArrayList<LectureInfo>();
        for(Lecture l:lectureList){
            lectureInfoList.add(new LectureInfo(l));
        }
        //对获取的讲座信息根据日期排序
        Collections.sort(lectureInfoList, new Comparator<LectureInfo>(){

            /*
             * 返回负数表示：lecture1 小于lecture2，
             * 返回0 表示：lecture1和lecture2相等，
             * 返回正数表示：lecture1大于lecture2。
             */
            public int compare(LectureInfo lecture1, LectureInfo lecture2) {
                Date d1=lecture1.getStartTime();
                Date d2=lecture2.getStartTime();
                int dateCompare=d1.compareTo(d2);
                return dateCompare;
            }
        });

        if(page<=0){
            return lectureInfoList;
        }

        int maxIndex=page*pageSize;
        int listSize=lectureInfoList.size();
        if(listSize<maxIndex){
            //这种情况返回最后一页的内容
           int startIndex=(listSize/pageSize)*pageSize;
           int endIndex=listSize;
           return lectureInfoList.subList(startIndex,endIndex);
        }else{
            int startIndex=(page-1)*pageSize;
            int endIndex=startIndex+pageSize;
            return lectureInfoList.subList(startIndex,endIndex);
        }



    }




    @Override
    public int getTotalPage(boolean all,int pageSize) {
        int recordNum=lectureDao.getTotalRecordNum();
        if(all==false){
            recordNum=lectureDao.fetchFutureList().size();
        }
        int totalPage=recordNum/pageSize;
        if(recordNum%pageSize==0){
            return totalPage;
        }else {
            return totalPage+1;
        }
    }



    @Override
    public LectureInfo lecture(int lid) {
        Lecture l=lectureDao.getLectureByLId(lid);
        return new LectureInfo(l);

    }

    @Override
    public void subscribe(LectureSubscriberInfo applicant) {
        LectureSubscriber l=applicant.toLectureSubscriber();
        lectureSubscriberDao.saveLectureSubscriberInfo(l);
    }

    @Override
    public void interest(String openId,int lid) {

        lectureDao.updateInterest(lid);
        lectureInteresterDao.saveLectureInterester(openId,lid);
    }

    @Override
    public List<LectureSubscriberInfo> getSubscribers(int lid) {

        List<LectureSubscriber> lectureSubsriberList=lectureSubscriberDao.getLectureSubscribers(lid);
        List<LectureSubscriberInfo> lectureSubscriberInfoList=new ArrayList<LectureSubscriberInfo>();
        for(LectureSubscriber l:lectureSubsriberList){
            lectureSubscriberInfoList.add(new LectureSubscriberInfo(l));
        }
        return lectureSubscriberInfoList;
    }

    @Override
    public boolean isInterest(String openId,int lid) {
        return lectureInteresterDao.getisInterest(openId,lid);
    }

    @Override
    public boolean isSubscriber(String openId,int lid) {

        return lectureSubscriberDao.getIsSubscriber(openId,lid);
    }

    @Override
    public List<LectureInfo> getInterestList(String openId) {
        List<LectureInfo> lectureInfoList=new ArrayList<LectureInfo>();
        List<LectureInterester> interestsList=lectureInteresterDao.getLectureInterestersByOpenId(openId);
        for(LectureInterester li:interestsList){
            Lecture l=li.getLecture();
            lectureInfoList.add(new LectureInfo(l));
        }
        return lectureInfoList;
    }

    @Override
    public List<LectureInfo> getSubscriberList(String openId) {
        List<LectureInfo> lectureInfoList=new ArrayList<LectureInfo>();
        List<LectureSubscriber> subscriberList=lectureSubscriberDao.getLectureSubscribersByOpenId(openId);
        for(LectureSubscriber ls:subscriberList){
            Lecture l=ls.getLecture();
            lectureInfoList.add(new LectureInfo(l));
        }
        return lectureInfoList;
    }


}
