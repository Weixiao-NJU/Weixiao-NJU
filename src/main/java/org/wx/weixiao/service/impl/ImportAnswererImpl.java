package org.wx.weixiao.service.impl;

import org.springframework.stereotype.Component;
import org.wx.weixiao.dao.ConstParametersDao;
import org.wx.weixiao.dao.DAOManager;
import org.wx.weixiao.model.*;
import org.wx.weixiao.service.ImportAnswererService;
import org.wx.weixiao.util.ErrorCodeUtil;
import org.wx.weixiao.util.ExcelUtil;

import java.util.ArrayList;

/**
 * Created by lizhimu on 2016/12/27.
 */
@Component("ImportAnswererService")
public class ImportAnswererImpl implements ImportAnswererService{
    @Override
    public int importKnowledgeInfo(String file_dir) {
        System.out.println("file directory is "+file_dir);
        String[] excelInfo=null;
        ArrayList<String> questionList=new ArrayList<String>();
        ArrayList<String> answerList=new ArrayList<String>();

        try {
            excelInfo = ExcelUtil.excelToArray(file_dir);
//            for(String s:excelInfo){
//                System.out.println(s);
//            }
        }catch(Exception e) {
            e.printStackTrace();
        }



        if(excelInfo!=null) {
            int excelInfoLength=excelInfo.length;
            if(excelInfoLength%2!=0||excelInfoLength==2){
                return ErrorCodeUtil.EXCEL_INTERNAL_FORMAT_WRONG;
            }
            //初始化各个属性的list
            for (int i = 2; i < excelInfo.length; i++) {
                String info=excelInfo[i];
                int type=i%2;
                switch(type) {
                    case 0:
                        questionList.add(info);
                        break;
                    case 1:
                        answerList.add(info);
                        break;
                }
            }

            if(answerList.size()<questionList.size()){
                return ErrorCodeUtil.EXCEL_INTERNAL_FORMAT_WRONG;
            }
        }

        for(int i=0;i<questionList.size();i++){

            String question=questionList.get(i);
            String answer=answerList.get(i);
            KnowledgeBase kb=new KnowledgeBase();
            kb.setQuestion(question);
            kb.setAnswer(answer);
            kb.setIsDelete(0);
            DAOManager.knowledgeBaseDao.save(kb);
        }
        return ErrorCodeUtil.SUCCESS;


    }
    @Override
    public int importAnswererInfo(String file_dir) {
        System.out.println("file directory is "+file_dir);
        //首先把excel转为String[],然后将String[]打包成
        // List<Answerer>,List<Department>,List<KeyWordAnswer>
        //一共要更新三个表
        //首先更新Answerer表
        //接着更新部门表
        //最后更新关键字表(直接更新)
        String[] excelInfo=null;
        ArrayList<String> userNameList=new ArrayList<String>();
        ArrayList<String> phoneNumberList=new ArrayList<String>();
        ArrayList<String> secretWordList=new ArrayList<String>();

        ArrayList<String> keyWordCollectorList=new ArrayList<String>();
        ArrayList<String> deptList=new ArrayList<String>();

        try {
            excelInfo = ExcelUtil.excelToArray(file_dir);
//            for(String s:excelInfo){
//                System.out.println(s);
//            }
        }catch(Exception e) {
            e.printStackTrace();
        }


        int excelInfoLength=excelInfo.length;
        if(excelInfoLength%5!=0||excelInfoLength==5){
            return ErrorCodeUtil.EXCEL_INTERNAL_FORMAT_WRONG;
        }



        //姓名,电话,绑定码,所在部门,匹配关键字
        //第一行是表的属性名，不写在循环中
        if(excelInfo!=null) {
            //初始化各个属性的list
            for (int i = 5; i < excelInfo.length; i++) {
                String info=excelInfo[i];
                int type=i%5;
                switch(type){
                    case 0:
                        userNameList.add(info);
                        break;
                    case 1:
                        phoneNumberList.add(info);
                        break;
                    case 2:
                        secretWordList.add(info);
                        break;
                    case 3:
                        deptList.add(info);
                        break;
                    default:
                        keyWordCollectorList.add(info);
                }
            }


            if(deptList.size()<userNameList.size()){
                return ErrorCodeUtil.EXCEL_INTERNAL_FORMAT_WRONG;
            }


            for(int i=0;i<userNameList.size();i++){

                String username=userNameList.get(i);
                String phoneNumber=phoneNumberList.get(i);
                String secretWord=secretWordList.get(i);
                String deptName=deptList.get(i);
                String keyWordCollector=keyWordCollectorList.get(i);
                System.out.println("deptName is "+deptName);
                Department dept=DAOManager.departmentDao.getDepartmentByName(deptName);
                if(dept==null){
                    //说明没有这个部门，我们先插入这个部门。
                    dept=new Department();
                    dept.setName(deptName);
                    dept.setIsDelete(0);
                    DAOManager.departmentDao.save(dept);
                }
                Answerer answerer=new Answerer();

                answerer.setName(username);
                answerer.setPhoneNum(phoneNumber);
                answerer.setSecretWord(secretWord);
                answerer.setDepartment(dept);
                answerer.setIsDelete(0);
                answerer.setMediaInfo(DAOManager.mediaInfoDao.getByMediaId("1"));
                DAOManager.answererDao.save(answerer);

                String[] keywordArray=keyWordCollector.split(";");
                for(String s:keywordArray){
                    KeywordAnswer keywordAnswer=new KeywordAnswer();
                    keywordAnswer.setAnswerer(answerer);
                    keywordAnswer.setKeyword(s);
                    keywordAnswer.setIsDelete(0);
                    DAOManager.keywordAnswererDao.save(keywordAnswer);
                }

            }

        }
        return ErrorCodeUtil.SUCCESS;

    }


}
