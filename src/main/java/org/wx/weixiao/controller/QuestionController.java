package org.wx.weixiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.wx.weixiao.Info.KeyWordInfo;
import org.wx.weixiao.Info.QuestionInfo;
import org.wx.weixiao.service.QuestionAndAnswerService;
import org.wx.weixiao.service.SearchAnswererService;
import org.wx.weixiao.util.AppConfigUtil;
import org.wx.weixiao.util.ErrorCodeUtil;
import org.wx.weixiao.util.SecurityUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zs14 on 2016/12/14.
 */
@Controller
public class QuestionController {
    @Resource(name = "QuestionAndAnswerService")
    QuestionAndAnswerService questionAndAnswerService;
    @Resource(name = "SearchAnswererService")
    SearchAnswererService searchAnswererService;

    //进入提出问题页
    @RequestMapping(value = "/ask_question",method = RequestMethod.GET)
        public ModelAndView askQuestion(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        String open_id = (String)request.getAttribute("open_id");
        String media_id = (String)request.getAttribute("media_id");
        //获得关键字列表
        List<KeyWordInfo> list=searchAnswererService.getAllKeyWord(media_id);
        //加密
        String open_id_encrypt = SecurityUtil.encode(open_id, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
        String media_id_encrypt = SecurityUtil.encode(media_id, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
        modelAndView.setViewName("question");
        modelAndView.addObject("open_id", open_id_encrypt);
        modelAndView.addObject("media_id",media_id_encrypt);
        modelAndView.addObject("k_list",list);
        return modelAndView;
    }

    //提交问题
    @RequestMapping(value = "/submit_question",method = RequestMethod.POST)
    public ModelAndView submitQuestion(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String open_id_encrypt = request.getParameter("openId");
        String mediaId_encrypt = request.getParameter("mediaId");
        //解密
        String open_id = SecurityUtil.decode(open_id_encrypt,AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
        String mediaId = SecurityUtil.decode(mediaId_encrypt,AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());

        String question= request.getParameter("question");
        System.out.println(question);
        String keyword = request.getParameter("keyword");
        QuestionInfo q = new QuestionInfo();
        q.setContent(question);
        q.setQuestioner_open_id(open_id);
        q.setKeyword(keyword);
        q.setQuestion_media_id(mediaId);
        int result=questionAndAnswerService.addQuestion(q);
        //构造ModelAndView
        ModelAndView modelAndView=new ModelAndView();
        if(result == ErrorCodeUtil.SUCCESS){
            modelAndView.setViewName("success");
            modelAndView.addObject("msg_title","提交成功");
            modelAndView.addObject("msg","你的问题已转发给\""+keyword+"\"问题的指导老师，请等候回复");
            return modelAndView;
        }else if(result == ErrorCodeUtil.QUESTION_NOT_DISPATCHED){
            modelAndView.setViewName("success");
            modelAndView.addObject("msg_title","提交成功");
            modelAndView.addObject("msg","你的问题已转发给\""+keyword+"\"问题的指导老师，目前不在工作时间内，时间到达工作时间后老师会及时回复");
            return modelAndView;

        }
        modelAndView.setViewName("fail");
        modelAndView.addObject("msg_title","提交失败");
        modelAndView.addObject("msg","请仔细检查输入，稍后再试。");
        return modelAndView;
    }
}
