package org.wx.weixiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.wx.weixiao.Info.AnswererInfo;
import org.wx.weixiao.Info.KnowledgeInfo;
import org.wx.weixiao.Info.LectureInfo;
import org.wx.weixiao.model.KnowledgeBase;
import org.wx.weixiao.service.*;
import org.wx.weixiao.util.AppConfigUtil;
import org.wx.weixiao.util.ErrorCodeUtil;
import org.wx.weixiao.util.SecurityUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zs14 on 2016/12/13.
 */
@Controller
public class AppConfigController {
    @Resource(name = "KnowledgeBaseService")
    KnowledgeBaseService knowledgeBaseService;
    @Resource(name = "SearchAnswererService")
    SearchAnswererService searchAnswererService;
    @Resource(name = "TimeSettingService")
    TimeSettingService timeSettingService;
    @Resource(name = "UploadService")
    UploadService uploadService;
    @Resource(name = "LectureService")
    LectureService lectureService;

    //进入应用配置页
    @RequestMapping(value = "/ask_config")
    public ModelAndView askConfig(HttpServletRequest request, HttpServletResponse respone) {
        String media_id = request.getParameter("media_id");
        //获得回答者名单
        List<AnswererInfo> answererInfos = searchAnswererService.getAllAnswererByMediaId(media_id);
        //获得知识库名单
        List<KnowledgeInfo> knowledgeInfos = knowledgeBaseService.getAllKnowledge(media_id);
        //获得讲座库名单
        List<LectureInfo> lectureInfos = lectureService.lectureList(false,1,10);
        int pageNum = lectureService.getTotalPage(false,10);
        //加密
        String media_id_encrypt = SecurityUtil.encode(media_id, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());

        ModelAndView view=new ModelAndView();
        view.setViewName("appConfig");
        view.addObject("answererInfos",answererInfos);
        view.addObject("media_id",media_id_encrypt);
        view.addObject("knowledgeInfos",knowledgeInfos);
        view.addObject("lectureInfos",lectureInfos);
        view.addObject("pageNum",pageNum);

        return view;
    }


    //提交配置
    @RequestMapping(value = "/submit_config",produces="text/html;charset=UTF-8;",method = RequestMethod.POST)
    @ResponseBody
    public String submitConfig(HttpServletRequest request,HttpServletResponse response) {

        String mediaId_encrypt = request.getParameter("media_id");
        String time1 = request.getParameter("time1");
        String time2 = request.getParameter("time2");
        String state = request.getParameter("state");

        //解密
        String media_id = SecurityUtil.decode(mediaId_encrypt,AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
        //设置时间
        timeSettingService.setTime(time1,time2,media_id);
        return "success";
    }

    //回答者/问答库示例表格下载
    @RequestMapping(value = "/file_download")
    public String fileDoload(@RequestParam String type,HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String fileName=null;
        if(type.equals("answerer")){
            fileName = "answerers";
        }
        else if(type.equals("qa")){
            fileName = "questions_answers";
        }else {
            return null;
        }

        String ctxPath = request.getSession().getServletContext().getRealPath("/") + "excel/";

        //System.out.println(ctxPath);

        String realPath=ctxPath+fileName+".xlsx";

        File file = new File(realPath);
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition","attachment;fileName=" + fileName+".xlsx");// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    //文件提交
    @RequestMapping(value = "/file_upload",produces="text/html;charset=UTF-8;",method = RequestMethod.POST)
    @ResponseBody
    public String fileUpLoad(@RequestParam String type,@RequestParam String media_id,HttpServletRequest request, HttpServletResponse response){
        //解密
        String mediaId = SecurityUtil.decode(media_id,AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
        System.out.println(mediaId);
        //TODO 文件安全
//        String path = request.getSession().getServletContext().getRealPath("/") + "excel/";
        String path = "/Users/zs/Documents/";
//        String path = "D:\\excelTest";
        int result = 0;
        if(type.equals("answerer")){
            result = uploadService.saveAnswererFileInDatabase(request,path,mediaId);
        }else if(type.equals("qa")){
            result = uploadService.saveKnowledgeFileInDatabase(request,path,mediaId);
        }
        String str = "上传成功";

        switch (result){
            case ErrorCodeUtil.POSTFIX_WRONG:
                str="上传文件类型错误";
                break;
            case ErrorCodeUtil.EXCEL_INTERNAL_FORMAT_WRONG:
                str="excel文件解析错误，请参考示例表格";
        }
        System.out.println(str);
        return str;
    }

    //配置页添加回答者
    @RequestMapping(value = "add_answerer",produces="text/html;charset=UTF-8;",method =RequestMethod.POST)
    @ResponseBody
    public String addAnswerer(HttpServletRequest request,HttpServletResponse response){
        AnswererInfo info = readAnswerer(request);
        System.out.print(info.toString());
        //调用逻辑层
        int ID=searchAnswererService.addAnswerer(info);
        if(ID==-1){
            return "添加失败";
        }
        return ID+"";
    }
    //配置页删除回答者
    @RequestMapping(value = "delete_answerer",produces="text/html;charset=UTF-8;",method =RequestMethod.POST)
    @ResponseBody
    public String deleteAnswerer(HttpServletRequest request,HttpServletResponse response){
        String id = request.getParameter("answererId");
        if(!id.matches("[0-9]+")){
            return "参数错误请再次尝试";
        }
        int result=searchAnswererService.deleteAnswerer(id);
        if(result != ErrorCodeUtil.SUCCESS)
            return "删除失败";
//        System.out.println(result);
        return "success";
    }
    //配置页修改回答者
    @RequestMapping(value = "modify_answerer",produces="text/html;charset=UTF-8;",method =RequestMethod.POST)
    @ResponseBody
    public String modifyAnswerer(HttpServletRequest request,HttpServletResponse response){
        AnswererInfo info = readAnswerer(request);
        String id = request.getParameter("id");
        info.setId(id);
        System.out.print(info.toString());
        //调用逻辑层
        int result=searchAnswererService.updateAnswerer(info);
        if(result!=ErrorCodeUtil.SUCCESS){
            return "修改失败";
        }
        return "success";
    }

    //配置页添加问答
    @RequestMapping(value = "add_qa",produces="text/html;charset=UTF-8;",method =RequestMethod.POST)
    @ResponseBody
    public String addQA(HttpServletRequest request,HttpServletResponse response){
        KnowledgeInfo info = readQA(request);
        System.out.print(info.toString());
        //调用逻辑层
        int ID=knowledgeBaseService.storeQuestion(info);
        if(ID==-1){
            return "添加失败";
        }
        return ID+"";
    }
    //配置页删除问答
    @RequestMapping(value = "delete_qa",produces="text/html;charset=UTF-8;",method =RequestMethod.POST)
    @ResponseBody
    public String deleteQA(HttpServletRequest request,HttpServletResponse response){
        String id = request.getParameter("id");
        if(!id.matches("[0-9]+")){
            return "参数错误请再次尝试";
        }
        int result=knowledgeBaseService.deleteKnowledge(id);
        if(result != ErrorCodeUtil.SUCCESS)
            return "删除失败";
//        System.out.println(result);
        return "success";
    }
    //配置页修改问答
    @RequestMapping(value = "modify_qa",produces="text/html;charset=UTF-8;",method =RequestMethod.POST)
    @ResponseBody
    public String modifyQA(HttpServletRequest request,HttpServletResponse response){
        KnowledgeInfo info = readQA(request);
        String id = request.getParameter("id");
        info.setId(id);
        System.out.print(info.toString());
        //调用逻辑层
        int result=knowledgeBaseService.updateKnowledge(info);
        if(result!=ErrorCodeUtil.SUCCESS){
            return "修改失败";
        }
        return "success";
    }
    //获取数据库文件的勿扰时间
    @RequestMapping(value = "get_time",produces="text/html;charset=UTF-8;",method =RequestMethod.POST)
    @ResponseBody
    public String getTime(HttpServletRequest request,HttpServletResponse response){
        String mediaId_encrypt = request.getParameter("media_id");
        //解密
        String media_id = SecurityUtil.decode(mediaId_encrypt,AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
        //获取勿扰时间
        String start = timeSettingService.readStartTime(media_id);
        String end = timeSettingService.readEndTime(media_id);
        //防御初次设置数据库无数据
        if(start == "" || start == null){
            start = "00:00";
        }
        if(end == "" || end == null){
            end = "00:00";
        }
        String time = start+":"+end;
        return time;
    }

    private AnswererInfo readAnswerer(HttpServletRequest request){
        String name = request.getParameter("name");
        String department = request.getParameter("department");
        String tel = request.getParameter("tel");
        String keyword = request.getParameter("keyword");
        String accountStr = request.getParameter("accountStr");
        String mediaId = request.getParameter("mediaId");
        //keyword处理
        keyword=keyword.replace("，",",");
        List<String> keys= Arrays.asList(keyword.split(","));
        //解密mediaId
        String media_id = SecurityUtil.decode(mediaId,AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
        //构造Answerer对象
        AnswererInfo info = new AnswererInfo("",name,department,tel,keys,accountStr,media_id);
        return info;
    }

    private KnowledgeInfo readQA(HttpServletRequest request){
        String question = request.getParameter("question");
        String answer = request.getParameter("answer");
        String mediaId = request.getParameter("mediaId");
        //解密mediaId
        String media_id = SecurityUtil.decode(mediaId,AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
        //构造Answerer对象
        KnowledgeInfo info = new KnowledgeInfo();
        info.setQuestion(question);
        info.setAnswer(answer);
        info.setMediaId(media_id);
        return info;
    }
}
