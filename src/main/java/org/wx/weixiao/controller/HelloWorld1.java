package org.wx.weixiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.wx.weixiao.service.AllDispatchCoreService;
import org.wx.weixiao.util.MessageUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 2016/12/26.
 */
@Controller
public class HelloWorld1 {

    @Resource(name = "AllDispatchCoreService")
    AllDispatchCoreService allDispatchCoreService;

    @RequestMapping("/hello1")
    public ModelAndView hello(@RequestParam(value = "id", defaultValue = "guest") String id) {
        System.out.println(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello");
        Map<String, String> paras = new HashMap<>();
        paras.put("FromUserName", "test");
        paras.put("ToUserName", "test");
        paras.put("MsgType", MessageUtil.REQ_MESSAGE_TYPE_TEXT);
        paras.put("Content", id);
        modelAndView.addObject("id", allDispatchCoreService.handleInput(paras));
        return modelAndView;
    }

}
