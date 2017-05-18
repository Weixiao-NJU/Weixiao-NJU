package org.wx.weixiao.controller;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wx.weixiao.common.LoggerManager;
import org.wx.weixiao.service.AllDispatchCoreService;
import org.wx.weixiao.service.InitMySQL;
import org.wx.weixiao.service.impl.ImportAnswererImpl;
import org.wx.weixiao.util.ApplicationContextHelper;

import javax.annotation.Resource;

/**
 * Created by Daniel on 2016/12/10.
 */
@Controller
public class Config {

    private final String token = "weixiao2017";

    @Resource(name = "AllDispatchCoreService")
    AllDispatchCoreService allDispatchCoreService;
    @Autowired
    ImportAnswererImpl importAnswerer;


    private static Logger logger = Logger.getLogger(Config.class);

//    @RequestMapping("/hello")
//    public ModelAndView hello(@RequestParam(value = "id", defaultValue = "guest") String id) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("hello");
//        Map<String, String> paras = new HashMap<>();
//        paras.put("FromUserName", "test");
//        paras.put("ToUserName", "test");
//        paras.put("MsgType", MessageUtil.REQ_MESSAGE_TYPE_TEXT);
//        paras.put("Content", id);
//        modelAndView.addObject("id", allDispatchCoreService.handleInput(paras));
//        modelAndView.addObject("id", id);
//        return modelAndView;
//    }

    @RequestMapping("/config")
    public void config(@RequestParam(value = "token") String token) {
        if (this.token.equals(token)) {
            InitMySQL.initSession();
            try {
               InitMySQL.addXMLDatatoMySql();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            InitMySQL.setUpIndex();
        }
    }

    @RequestMapping("/importInfos")
    public void importInfos(@RequestParam(value = "token") String token, @RequestParam
            (value = "path", defaultValue = "excel/AnswererInfo.xlsx") String path) {
        if (this.token.equals(token)) {
            importAnswerer.importAnswererInfo(path);
        }
    }

    @RequestMapping("/indexData")
    public void initIndex(@RequestParam(value = "token") String token){
        if(!token.equals(token))
            return;
        ApplicationContext cxt = ApplicationContextHelper.getApplicationContext();
        SessionFactory sessionFactory = (SessionFactory) cxt.getBean("sessionFactory");
        FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.openSession());
        try {
            fullTextSession.createIndexer().startAndWait();
            LoggerManager.info(logger,"Init Data Index");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
