package org.wx.weixiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.wx.weixiao.Info.QuestionInfo;
import org.wx.weixiao.service.StudyInquiryService;
import org.wx.weixiao.util.ListType;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zs on 2017/1/16.
 */
@Controller
public class StudyInquiryController {

    @Resource(name = "StudyInquiryService")
    StudyInquiryService studyInquiryService;

    //学生-我的提问列表
    @RequestMapping(value = "/my_question_list")
    public ModelAndView myQuestionList(HttpServletRequest request, HttpServletResponse response){
        String open_id=(String)request.getAttribute("open_id");
        if(open_id==null){
            return getDangerView();
        }
        List<QuestionInfo> list = studyInquiryService.getAllQuestion(open_id);
        ModelAndView view=getQuestionView("我的提问列表",list);
        view.addObject("type", ListType.MYQ);
        view.addObject("info","最近还没有提过问题哦！");
        return view;
    }

    //老师-我的回答列表
    @RequestMapping(value = "/my_answer_list")
    public ModelAndView myAnswerList(HttpServletRequest request, HttpServletResponse response){
        String answer_id=(String)request.getAttribute("answerer_id");
        if(answer_id==null){
            return getDangerView();
        }
        List<QuestionInfo> list = studyInquiryService.getMyAnswers(answer_id);
        ModelAndView view=getQuestionView("我的回答列表",list);
        view.addObject("type", ListType.MYA);
        view.addObject("info","最近还没有回答过问题哦！");
        return view;
    }

    //老师-未解决问题列表-单击跳转到回答页
    @RequestMapping(value = "/unsolved_question_list")
    public ModelAndView unsolvedQuestionList(HttpServletRequest request, HttpServletResponse response){
        String answer_id=(String)request.getAttribute("answerer_id");
        if(answer_id==null){
            return getDangerView();
        }
        //TODO 暂无接口
        List<QuestionInfo> list = studyInquiryService.getUnsolvedQuestions(answer_id);

        ModelAndView view=getQuestionView("未解决问题列表",list);
        view.addObject("answer_id", answer_id);
        view.addObject("type", ListType.UQ);
        view.addObject("info","没有待解决的问题哦！");
        return view;

    }

    //公众-最近公开问答
    @RequestMapping(value = "/public_QA_list")
    public ModelAndView publicQAList(HttpServletRequest request, HttpServletResponse response){

        String media_id=(String)request.getAttribute("media_id");
        if(media_id==null){
            return getDangerView();
        }
        List<QuestionInfo> list = studyInquiryService.getRecentOpenQuestion(media_id);
        ModelAndView view=getQuestionView("最近公开问答",list);
        view.addObject("type", ListType.PQA);
        view.addObject("info","最近还没有公开问答哦！");
        return view;
    }

    @RequestMapping(value = "/onDuty")
    public ModelAndView onDuty(HttpServletRequest request,HttpServletResponse response){
        String media_id=(String)request.getAttribute("media_id");
        String answerer_id=(String)request.getAttribute("answerer_id");
        List<QuestionInfo> list = studyInquiryService.getUnsolvedQuestionOnDuty(media_id,answerer_id);
        ModelAndView view = getQuestionView("值班未解决问题",list);
        view.addObject("type",ListType.ONDUTY);
        view.addObject("info","今日暂时没有需要解答的问题");
        return view;
    }


    //装入问题列表界面
    private ModelAndView getQuestionView(String title,List<QuestionInfo> list){
        ModelAndView view = new ModelAndView("question_list");
        view.addObject("title",title);
        view.addObject("list",list);
        return view;
    }
//    //装入问答列表界面
//    private ModelAndView getQuestionAndAnswerView(String title,List list){
//        ModelAndView view = new ModelAndView("question_answer_list");
//        view.addObject("title",title);
//        view.addObject("list",list);
//        return view;
//    }
    //危险界面
    private ModelAndView getDangerView(){
        ModelAndView view = new ModelAndView("danger");
        view.addObject("msg_title", "危险警告");
        view.addObject("msg", "此次操作不被允许！");
        return view;
    }

}
