package org.wx.weixiao.dao;

import org.springframework.stereotype.Repository;
import org.wx.weixiao.dao.base.BaseDao;
import org.wx.weixiao.model.Department;

import java.util.List;

/**
 * Created by Daniel on 2016/12/26.
 */
@Repository
public class DepartmentDao extends BaseDao<Department, Integer> {
    public List<Department> getListByColumnValue(String colName, Object colValue){
        String hqlString="select distinct q from Department q where q."+colName+"=?";
        return getListByHQL(hqlString,colValue);
    }

    public Department getDepartmentByName(String deptName) {
        String hqlString = "select distinct q from Department q where q.name=?";
        Department department = getByHQL(hqlString, deptName);
        return department;
    }
        //Department list=getByHQL("select distinct q from Department q where q.name=?",deptName);
//        List<Department> list=getListByHQL("from Department where name=?",deptName);
//        Department d=list.get(0);
//        if(d!=null){
//
//            return d;
//        }else{
//
//            return null;
//        }



}
