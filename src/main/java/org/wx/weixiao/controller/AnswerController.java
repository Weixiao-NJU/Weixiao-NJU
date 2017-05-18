package org.wx.weixiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.wx.weixiao.Info.AnswerInfo;
import org.wx.weixiao.Info.AnswererInfo;
import org.wx.weixiao.Info.QuestionInfo;
import org.wx.weixiao.service.KnowledgeBaseService;
import org.wx.weixiao.service.QuestionAndAnswerService;
import org.wx.weixiao.service.SearchAnswererService;
import org.wx.weixiao.util.AppConfigUtil;
import org.wx.weixiao.util.ErrorCodeUtil;
import org.wx.weixiao.util.SecurityUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darxan on 2016/12/18.
 * <p>
 * personal log:
 * need a logic to get the right answer
 * 1. 需要获得用户未回答的问题
 * 2. 用户提交的问题答案的时候需要回答的数据
 */
@Controller
public class AnswerController {

    @Resource(name = "KnowledgeBaseService")
    KnowledgeBaseService knowledgeBaseService;
    @Resource(name = "QuestionAndAnswerService")
    QuestionAndAnswerService questionAndAnswerService;
    @Resource(name = "SearchAnswererService")
    SearchAnswererService searchAnswererService;

//    @Autowired
//    public AnswerController(KnowledgeBaseService knowledgeBaseService){
//        this.knowledgeBaseService = knowledgeBaseService;
//    }

//    @RequestMapping("/answer")
//    public Object answer( String answerUserId, String quest, String answer){
//        KnowledgeBase question = new KnowledgeBase();
//        question.setAnswer(answer);
//        question.setAnswerUserId(answerUserId);
//        question.setCreateAt(new Date());
//        return knowledgeBaseService.storeQuestion(question);
//    }

    @RequestMapping("/questions")
    public Object getNoAnswerQuestions() {
        return null;
    }

    @RequestMapping("/unsolved")
    public Object unsolved(@RequestParam(defaultValue = "") String question) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("question");
        return modelAndView;
    }


    @RequestMapping("/answer_question")
    public ModelAndView answerQuestion(HttpServletRequest request,HttpServletResponse response) {
        //构造ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        String question_id = (String)request.getAttribute("question_id");
        String answerer_id = (String)request.getAttribute("answerer_id");
        int flag = Integer.parseInt((String)request.getAttribute("flag"));
        //获取问题内容
        QuestionInfo question = questionAndAnswerService.searchQuestion(question_id);
        //List<AnswerInfo> answerList = questionAndAnswerService.getAnswers(question_id);
        //if (answerList.size() != 0 && flag == 0) {
        if(question.getIsAnswered()==1 && flag == 0 ){
            modelAndView.setViewName("existinganswer");
            Map<String,String> paras = new HashMap<>();
            paras.put("question_id",question_id);
            paras.put("answerer_id",answerer_id);
            paras.put("flag",1+"");
            String encryptcode = SecurityUtil.encode(paras, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
            modelAndView.addObject("url", String.format
                    ("/core/answer_question?encrypt=%s", encryptcode));
            return modelAndView;
        }
        //防御问题不存在
        if (question == null || question.getId().equals("0")) {
            modelAndView.setViewName("fail");
            modelAndView.addObject("msg_title", "访问失败");
            modelAndView.addObject("msg", "该问题不存在");
            return modelAndView;
        }
        //设置

        String question_id_encrypt = SecurityUtil.encode(question_id, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
        String answerer_id_encrypt = SecurityUtil.encode(answerer_id, AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
        //设置session
        HttpSession session=request.getSession(true);
        session.setAttribute("question_id",question_id);
        session.setAttribute("answerer_id",answerer_id);
        session.setAttribute("question",question.getContent());
        //modelAndView
        modelAndView.setViewName("answer");
        modelAndView.addObject("question", question);
        modelAndView.addObject("question_id", question_id_encrypt);
        modelAndView.addObject("answerer_id", answerer_id_encrypt);
        return modelAndView;
    }



    @RequestMapping(value = "/submit_answer", method = RequestMethod.POST)
    public ModelAndView submitAnswer(HttpServletRequest request, HttpServletResponse response) {
        //确保中文编码
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //获取参数
        String answererId_encrypt = request.getParameter("answererId");
        String questionId_encrypt = request.getParameter("questionId");
        //解码
        String answererId = SecurityUtil.decode(answererId_encrypt,AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());
        String questionId = SecurityUtil.decode(questionId_encrypt,AppConfigUtil.get(AppConfigUtil.ALLDISPATCH).getToken());

        String answer = request.getParameter("answer");
        String power = request.getParameter("power");
        //构造存储对象
        AnswerInfo info = new AnswerInfo();
        info.setQuestionId(questionId);
        info.setContent(answer);
        info.setAnswererId(answererId);
        //info.setAnswererName("拿不到，应该要去后台找");
        boolean isOpen = (power.equals("public"));
        info.setOpen(isOpen);
//        System.out.println(info.toString());
        int result = questionAndAnswerService.addAnswer(info);
        //session添加回答
        HttpSession session=request.getSession();
        session.setAttribute("answer",answer);
        //构造ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        if (result == ErrorCodeUtil.SUCCESS) {
            modelAndView.setViewName("answer_success");
            modelAndView.addObject("msg_title", "提交成功");
            modelAndView.addObject("msg", "您的回答已转发给提问的同学");
            return modelAndView;
        }
        modelAndView.setViewName("fail");
        modelAndView.addObject("msg_title", "提交失败");
        modelAndView.addObject("msg", "请仔细检查输入，稍后再试。");
        return modelAndView;
    }


    @RequestMapping(value = "/forward", method = RequestMethod.GET)
    public ModelAndView forwardQuestion(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        //获取参数
        HttpSession session=request.getSession(true);
        String answererId = (String)session.getAttribute("answerer_id");
        String questionId = (String)session.getAttribute("question_id");
        String mediaId = knowledgeBaseService.getMediaIdbyAnswererId(answererId);
        if(answererId==null||questionId==null){
            return getDangerView();
        }
        //获取回答者
        List<AnswererInfo> list=searchAnswererService.getAllAnswererByMediaId(mediaId);
        if(list.isEmpty()){
            modelAndView.setViewName("fail");
            modelAndView.addObject("msg_title", "转发失败");
            modelAndView.addObject("msg", "没有对应的回答老师哦，请先去添加回答者");
            return modelAndView;
        }
        //构造ModelAndView
        modelAndView.setViewName("forward");
        modelAndView.addObject("answerers",list);
        return modelAndView;
    }

    @RequestMapping(value = "/submit_forward", method = RequestMethod.POST)
    public ModelAndView summitForward(HttpServletRequest request, HttpServletResponse response) {
        //确保中文编码
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView();
        //获取参数
        HttpSession session=request.getSession();
        String answererId = (String)session.getAttribute("answerer_id");
        String questionId = (String)session.getAttribute("question_id");
        String teacher = request.getParameter("teachers");
        //TODO 防御注入参数
        if(answererId.contains("=")||questionId.contains("=")){
            return getDangerView();
        }

        //构造发送和接收回答者
        AnswererInfo from = searchAnswererService.getAnswererById(answererId);
        AnswererInfo to = new AnswererInfo();
        to.setId(teacher);

        System.out.print(answererId+" "+questionId+" "+teacher);
        int result=questionAndAnswerService.dispatchQuestion(from,to,questionId);
        if (result == ErrorCodeUtil.SUCCESS) {
            modelAndView.setViewName("success");
            modelAndView.addObject("msg_title", "转发成功");
            modelAndView.addObject("msg", "该问题已转发给您选择的回答者");
            return modelAndView;
        }
        return modelAndView;
    }

    private ModelAndView getDangerView(){
        ModelAndView view = new ModelAndView("danger");
        view.addObject("msg_title", "危险警告");
        view.addObject("msg", "此次操作不被允许！");
        return view;
    }

}
