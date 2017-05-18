package org.wx.weixiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.wx.weixiao.Info.KnowledgeInfo;
import org.wx.weixiao.service.KnowledgeBaseService;
import org.wx.weixiao.util.ErrorCodeUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Jerry Wang on 2016/12/24.
 */
@Controller
public class KnowledgeBaseController {

    @Resource(name = "KnowledgeBaseService")
    KnowledgeBaseService knowledgeBaseService;

    @RequestMapping("/knowledgeBase")
    public ModelAndView knowledgeBase(@RequestParam(value = "id", defaultValue = "0") String id) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("knowledge");
        KnowledgeInfo info = knowledgeBaseService.getKnowledgeBaseById(Integer.parseInt(id));
        modelAndView.addObject("knowledge", info);
        return modelAndView;
    }

    @RequestMapping("/add_knowledge")
    public ModelAndView addKnowledgeBase(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if(session==null){
            ModelAndView view = new ModelAndView("danger");
            view.addObject("msg_title", "危险警告");
            view.addObject("msg", "此次操作不被允许！");
            return view;
        }
        String question = (String) session.getAttribute("question");
        String answer = (String) session.getAttribute("answer");
        //构造modelAndView
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add_knowledge");
        modelAndView.addObject("question",question);
        modelAndView.addObject("answer",answer);
        return modelAndView;
    }

    @RequestMapping(value = "/sumbit_knowledge",method = RequestMethod.POST)
    public ModelAndView submitKnowledgeBase(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession(false);
        if(session==null){
            ModelAndView view = new ModelAndView("danger");
            view.addObject("msg_title", "危险警告");
            view.addObject("msg", "此次操作不被允许！");
            return view;
        }
        String mediaId =knowledgeBaseService.getMediaIdbyAnswererId((String)session.getAttribute("answerer_id"));

        String new_question = request.getParameter("new_question");
        String new_answer = request.getParameter("new_answer");

        //构造一条问答
        KnowledgeInfo info = new KnowledgeInfo();
        info.setQuestion(new_question);
        info.setAnswer(new_answer);
        info.setMediaId(mediaId);

        int result=knowledgeBaseService.storeQuestion(info);
        //构造ModelAndView
        ModelAndView modelAndView=new ModelAndView();
        if(result == ErrorCodeUtil.SUCCESS){
            modelAndView.setViewName("success");
            modelAndView.addObject("msg_title","添加成功");
            modelAndView.addObject("msg","该问答已经添加至知识库");
            return modelAndView;
        }
        modelAndView.setViewName("fail");
        modelAndView.addObject("msg_title","添加失败");
        modelAndView.addObject("msg","请仔细检查输入，稍后再试。");
        return modelAndView;
    }


    //公众-最近公开问答
    @RequestMapping(value = "/heatKnowledgeBase")
    public ModelAndView publicQAList(HttpServletRequest request, HttpServletResponse response){

        String media_id=(String)request.getAttribute("media_id");
        //String media_id = request.getParameter("media_id");
        if(media_id==null){
            return getDangerView();
        }
        List<KnowledgeInfo> list = knowledgeBaseService.getHeatKnowledge(media_id);
        ModelAndView view=new ModelAndView("hotKnowledge");
        view.addObject("list",list);
        view.addObject("title","热门回答");
        view.addObject("info","当前还没有较热门的回答！");
        return view;
    }

    //危险界面
    private ModelAndView getDangerView(){
        ModelAndView view = new ModelAndView("danger");
        view.addObject("msg_title", "危险警告");
        view.addObject("msg", "此次操作不被允许！");
        return view;
    }
}
