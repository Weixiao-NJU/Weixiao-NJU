package org.wx.weixiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.wx.weixiao.Info.LectureInfo;
import org.wx.weixiao.Info.LectureSubscriberInfo;
import org.wx.weixiao.service.LectureService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs on 2017/4/25.
 */
@Controller
public class LectureController {
    @Resource(name = "LectureService")
    LectureService lectureService;

    @RequestMapping(value = "/all_lectures")
    public ModelAndView allLectures(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("lectures");
        String open_id = (String)request.getAttribute("open_id");
//        String open_id = "o25Awv8hPme5_McKzPvHeucpnrrE";
        HttpSession session = request.getSession();
        session.setAttribute("open_id",open_id);
        List<LectureInfo> list = lectureService.lectureList(false,1,5);
        view.addObject("list",list);
        view.addObject("title","全部讲座");
        view.addObject("type",1);
        return view;
    }

    @RequestMapping(value = "/interest_lectures")
    public ModelAndView interestLectures(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("lectures");
        String open_id = (String)request.getAttribute("open_id");
        HttpSession session = request.getSession();
        session.setAttribute("open_id",open_id);
        List<LectureInfo> list = lectureService.getInterestList(open_id);
        view.addObject("list",list);
        view.addObject("title","我感兴趣的讲座");
        view.addObject("type",3);
        return view;
    }

    @RequestMapping(value = "/joined_lectures")
    public ModelAndView joinedLectures(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("lectures");
        String open_id = (String)request.getAttribute("open_id");
        HttpSession session = request.getSession();
        session.setAttribute("open_id",open_id);
        List<LectureInfo> list = lectureService.getSubscriberList(open_id);
        view.addObject("list",list);
        view.addObject("title","我参加的讲座");
        view.addObject("type",2);
        return view;
    }

    @RequestMapping(value = "/get_lecture_list",method = RequestMethod.POST)
    @ResponseBody
    public List<LectureInfo> getLectureList(HttpServletRequest request, HttpServletResponse response){
        String pageStr = request.getParameter("pageNum");
        if(pageStr ==null || pageStr == "")
            return new ArrayList<>();
        int page = Integer.parseInt(pageStr);

        int pages = lectureService.getTotalPage(false,10);
        if(page>pages){
            return new ArrayList<>();
        }
        List<LectureInfo> list = lectureService.lectureList(false,page,10);
        return list;
    }

    @RequestMapping(value = "/lecture")
    public ModelAndView lecture(@RequestParam String lid , HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView("lecture_detail");

        int lId = 0;
        try {
            lId = Integer.parseInt(lid);
        }catch (NumberFormatException e){
            view.setViewName("fail");
            view.addObject("msg_title","打开失败");
            view.addObject("msg","请仔细检查网址，稍后再试。");
            return view;
        }
        LectureInfo detail = lectureService.lecture(lId);
        //获得对应的open-id
        HttpSession session = request.getSession();
        String open_id = (String)session.getAttribute("open_id");
        boolean isInterest = lectureService.isInterest(open_id,detail.getLid());
        boolean isSubscriber = lectureService.isSubscriber(open_id,detail.getLid());

        if(detail == null){
            view.setViewName("fail");
            view.addObject("msg_title","打开失败");
            view.addObject("msg","请仔细检查网址，稍后再试。");
            return view;
        }
        view.addObject("detail",detail);
        view.addObject("isInterest",isInterest);
        view.addObject("isSubscriber",isSubscriber);
        return view;
    }
    @RequestMapping(value = "/join_lecture")
    public ModelAndView joinLecture(@RequestParam String lid, HttpServletRequest request,HttpServletResponse response){
        ModelAndView view = new ModelAndView("join");
        view.addObject("lid",lid);
        return view;
    }

    @RequestMapping(value = "/submit_join")
    public ModelAndView submitJoin(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ModelAndView fail = new ModelAndView("fail");
        fail.addObject("msg_title","参加失败");
        fail.addObject("msg","请仔细检查输入（如是否非法输入），稍后再试。");
        //获取参数
        String email= request.getParameter("email");
        String phone = request.getParameter("phone");
        String lid = request.getParameter("lid");
        HttpSession session = request.getSession();
        String open_id = (String)session.getAttribute("open_id");
        //参数验证
        String regex = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
        String regPho = "^[1][3|4|5|8][0-9]{9}$";
        if(email != null && email != "" && email.matches(regex)
                && phone !=null && phone != "" && phone.matches(regPho)) {
            LectureSubscriberInfo info  = new LectureSubscriberInfo();
            info.setEmail(email);
            info.setPhone(phone);
            info.setOpenid(open_id);
            info.setLectureLId(Integer.parseInt(lid));
            lectureService.subscribe(info);
            ModelAndView view = new ModelAndView("join_success");
            view.addObject("lid", lid);
            return view;
        }
        return fail;
    }

    @RequestMapping(value = "/interest")
    @ResponseBody
    public String interst(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String open_id = (String)session.getAttribute("open_id");
        int lid = Integer.parseInt(request.getParameter("lid"));
        lectureService.interest(open_id,lid);
        return "success";
    }
}
