package org.wx.weixiao.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.wx.weixiao.dao.base.BaseDao;
import org.wx.weixiao.model.Lecture;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lizhimu on 2017/4/26.
 */
@Repository
public class LectureDao extends BaseDao<Lecture,Integer> {


    public List<Lecture> getShouldEmailsLecture(Date start, Date before) {
        List<Lecture> lectures = null;
        if (start==null && before==null) {
            lectures = this.fetchAllList();
        }
        else if (start==null) {
            lectures = getListByHQL("from Lecture lect where lect.startTime<=?", before);
        }
        else if (before==null) {
            lectures = getListByHQL("from Lecture lect where lect.startTime>=?", start);
        } else {
            String hql = "from Lecture lect where lect.startTime>=? and lect.startTime<=?";
            lectures = getListByHQL(hql, start, before);
        }
        lectures.forEach(lecture -> System.out.println(lecture.getSubscriberList().size()));
        return lectures;
    }

    public int getNewestLectureId() {
        try {
            String hql = "select max(lect.id)  from Lecture lect";
            Query query = getSession().createQuery(hql);
            Integer id = (Integer) query.uniqueResult();
            return id==null?-1:id;
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Lecture> getListByColumnValue(String colName,Object colValue){
        String hqlString="from Lecture q where q."+colName+"=?";
        return getListByHQL(hqlString,colValue);
    }

    public Lecture getLectureById(int id) {
        List<Lecture>list=getListByColumnValue("id",id);
        if(!list.isEmpty()){
            return list.get(0);
        }else{
            return null;
        }
    }
    public Lecture getLectureByLId(int lid) {
        List<Lecture>list=getListByColumnValue("lid",lid);
        if(!list.isEmpty()){
            return list.get(0);
        }else{
            return null;
        }
    }


    public List<Lecture> fetchHistoryList() {
        Date currentDate=new Date();
        List<Lecture> allList=fetchAllList();
        List<Lecture> historyList=new ArrayList<Lecture>();
        for(Lecture l:allList){
            Date lectureDate=l.getStartTime();
            if(lectureDate.compareTo(currentDate)<0){
                historyList.add(l);
            }
        }
        return historyList;
    }


    public List<Lecture> fetchFutureList() {
        Date currentDate=new Date();
        List<Lecture> allList=fetchAllList();
        List<Lecture> historyList=new ArrayList<Lecture>();
        for(Lecture l:allList){
            Date lectureDate=l.getStartTime();
            if(lectureDate.compareTo(currentDate)>=0){
                historyList.add(l);
            }
        }
        return historyList;
    }

    public int getTotalRecordNum() {
        return fetchAllList().size();
    }



    public void updateInterest(int lid) {
        Lecture l=null;
        List<Lecture>list=getListByColumnValue("lid",lid);
        if(!list.isEmpty()){
            l=list.get(0);
            l.setInterestNum(l.getInterestNum()+1);
            update(l);
        }

    }

    private List<Lecture> fetchAllList(){
        String hqlString="from Lecture q";
        return getListByHQL(hqlString);
    }

}
