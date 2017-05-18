package org.wx.weixiao.service.impl;

import org.springframework.stereotype.Component;
import org.wx.weixiao.Info.AnswererInfo;
import org.wx.weixiao.Info.KeyWordInfo;
import org.wx.weixiao.dao.DAOManager;
import org.wx.weixiao.model.Answerer;
import org.wx.weixiao.model.Department;
import org.wx.weixiao.model.KeywordAnswer;
import org.wx.weixiao.model.MediaInfo;
import org.wx.weixiao.service.SearchAnswererService;
import org.wx.weixiao.util.ErrorCodeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zs14 on 2016/12/27.
 */
@Component("SearchAnswererService")
public class SearchAnswererImpl implements SearchAnswererService {


    @Override
    public List<AnswererInfo> getAllAnswerer(String mId) {
        List<AnswererInfo> answererInfos = new ArrayList<>();
        try {
            List<Answerer> re = DAOManager.answererDao.getAnswerersByMediaID(Integer.parseInt(mId));
            for (Answerer ans : re) {
                if(ans.getOpenId()==null)
                    continue;

                AnswererInfo answererInfo = new AnswererInfo();
                answererInfo.setId(ans.getAnswId() + "");
                answererInfo.setName(ans.getName());

                List<String> keyWordInfoList = DAOManager.keywordAnswererDao.getKeyWordByAnswererId(ans.getAnswId()).stream().map(KeywordAnswer::getKeyword
                ).collect(Collectors.toList());

                answererInfo.setKeyword(keyWordInfoList);
                answererInfo.setDepartment(ans.getDepartment().getName());
                answererInfo.setTelephone(ans.getPhoneNum());
                answererInfo.setAccountStr(ans.getSecretWord());
                answererInfos.add(answererInfo);

            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return answererInfos;
    }

    @Override
    public List<AnswererInfo> getAllAnswererByMediaId(String mediaId) {
        List<AnswererInfo> re = null;
        try {
            int mid = DAOManager.mediaInfoDao.getByMediaId(mediaId).getMid();
            re = getAllAnswerer(mid+"");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return re;
    }

    @Override
    public List<KeyWordInfo> getAllKeyWord(String mediaId) {
        int media_Id = DAOManager.mediaInfoDao.getByMediaId(mediaId).getMid();
        List<Answerer> answerers = DAOManager.answererDao.getAnswerersByMediaID(media_Id);

        List<KeyWordInfo> keyWordInfos = new ArrayList<>();

        for(Answerer answerer:answerers) {
            List<KeywordAnswer> re = DAOManager.keywordAnswererDao.getKeyWordByAnswererId(answerer.getAnswId());
            for (KeywordAnswer r : re) {
                //未绑定不予显示
                if (r.getAnswerer().getOpenId() == null)
                    continue;
                KeyWordInfo kk = new KeyWordInfo();
                kk.setKeyword(r.getKeyword());
                keyWordInfos.add(kk);
            }

        }
        return keyWordInfos;
    }

    @Override
    public AnswererInfo getAnswererById(String answererId) {
        Answerer answerer = DAOManager.answererDao.get(Integer.parseInt(answererId));
        AnswererInfo re = new AnswererInfo();
        re.setId(answererId);
        re.setName(answerer.getName());
        re.setKeyword(answerer.getKeywordAnswer().stream().map(KeywordAnswer::getKeyword
        ).collect(Collectors.toList()));
        re.setAccountStr(answerer.getSecretWord());
        re.setTelephone(answerer.getPhoneNum());
        re.setDepartment(answerer.getDepartment().getName());
        return re;
    }

    @Override
    public int updateAnswerer(AnswererInfo answererInfo) {
        try {
            Answerer answerer = DAOManager.answererDao.get(Integer.parseInt(answererInfo.getId()));
            answerer.setName(answererInfo.getName());
            answerer.setPhoneNum(answererInfo.getTelephone());

            Department department = DAOManager.departmentDao.getDepartmentByName(answererInfo.getDepartment());
            //如果没有新建一个
            if(department==null){
                department = new Department();
                department.setIsDelete(0);
                department.setName(answererInfo.getDepartment());
                DAOManager.departmentDao.save(department);
            }
            answerer.setDepartment(department);
            List<KeywordAnswer> keywordAnswerList = new ArrayList<>();

            for(KeywordAnswer keywordAnswer:answerer.getKeywordAnswer()){
                //如果原来的关键字被删了
                if(!answererInfo.getKeyword().contains(keywordAnswer.getKeyword()))
                    DAOManager.keywordAnswererDao.fakeDelete(keywordAnswer);
            }
            for(String key:answererInfo.getKeyword()){

                KeywordAnswer kk = DAOManager.keywordAnswererDao.getKeyWordBykeyword_AnswererId(key,answerer.getAnswId());
                //如果没有检查一下是否原来有新建一个
                if(kk == null){
                    kk = DAOManager.keywordAnswererDao.getDeletedKeyWord(key,answerer.getAnswId());
                    if(kk==null) {
                        System.out.println(kk);
                        kk = new KeywordAnswer();
                        kk.setKeyword(key);
                        kk.setIsDelete(0);
                        kk.setAnswerer(answerer);
                        DAOManager.keywordAnswererDao.save(kk);
                    }else{
                        kk.setIsDelete(0);
                        DAOManager.keywordAnswererDao.update(kk);
                    }
                }
                keywordAnswerList.add(kk);
            }
            answerer.setKeywordAnswer(keywordAnswerList);
            DAOManager.answererDao.update(answerer);

        }catch (NumberFormatException e){
            return ErrorCodeUtil.NUMBER_FORMAT_ERROR;
        }catch (Exception e){
            e.printStackTrace();
            return ErrorCodeUtil.UNKNOW_FAILURE;
        }
        return ErrorCodeUtil.SUCCESS;
    }

    @Override
    public int deleteAnswerer(String answererId) {
        try {
            DAOManager.answererDao.fakeDeleteById(Integer.parseInt(answererId));
        }catch (NumberFormatException e){
            return ErrorCodeUtil.NUMBER_FORMAT_ERROR;
        }catch (Exception e){
            return ErrorCodeUtil.UNKNOW_FAILURE;
        }
        return ErrorCodeUtil.SUCCESS;
    }

    @Override
    public int addAnswerer(AnswererInfo answererInfo) {
        //TODO 失败返回-1，成功为ID，这里随意给了一个
        Answerer answerer = new Answerer();
        try{
            answerer.setIsDelete(0);
            answerer.setName(answererInfo.getName());
            answerer.setPhoneNum(answererInfo.getTelephone());

            Department department = DAOManager.departmentDao.getDepartmentByName(answererInfo.getDepartment());
            //如果没有新建一个
            if(department==null){
                department = new Department();
                department.setIsDelete(0);
                department.setName(answererInfo.getDepartment());
                DAOManager.departmentDao.save(department);
            }
            MediaInfo mediaInfo = DAOManager.mediaInfoDao.getByMediaId(answererInfo.getMediaId());
            answerer.setDepartment(department);
            answerer.setSecretWord(answererInfo.getAccountStr());
            answerer.setMediaInfo(mediaInfo);

            DAOManager.answererDao.saveOrUpdate(answerer);

            for(String keyword:answererInfo.getKeyword()) {
                KeywordAnswer keywordAnswer = new KeywordAnswer();
                keywordAnswer.setKeyword(keyword);
                keywordAnswer.setIsDelete(0);
                keywordAnswer.setAnswerer(answerer);
                DAOManager.keywordAnswererDao.saveOrUpdate(keywordAnswer);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ErrorCodeUtil.ADD_ANSWERER_FAIL;
        }
        return answerer.getAnswId();
    }
}
