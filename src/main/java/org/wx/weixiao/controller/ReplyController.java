
package org.wx.weixiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wx.weixiao.Info.AnswerInfo;
import org.wx.weixiao.Info.QuestionInfo;
import org.wx.weixiao.service.QuestionAndAnswerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 负责问题被回答后返回给提问者的问答详情调用
 */
@Controller
public class ReplyController {

    @Resource(name = "QuestionAndAnswerService")
    QuestionAndAnswerService questionAndAnswerService;


    @RequestMapping("/get_reply")
    public ModelAndView getReply(HttpServletRequest request, HttpServletResponse response){
        String question_id = (String)request.getAttribute("question_id");
        //构造ModelAndView
        ModelAndView modelAndView=new ModelAndView();
        //获取问题内容
        QuestionInfo question=questionAndAnswerService.searchQuestion(question_id);
        //防御问题不存在
        if(question==null){
            modelAndView.setViewName("fail");
            modelAndView.addObject("msg_title","访问失败");
            modelAndView.addObject("msg","该问题不存在");
            return modelAndView;
        }
        //获取回答列表
        List<AnswerInfo> list=questionAndAnswerService.getAnswers(question_id);
        //设置
        modelAndView.setViewName("reply");
        modelAndView.addObject("question",question);
        modelAndView.addObject("answers",list);
        return modelAndView;
    }
}
