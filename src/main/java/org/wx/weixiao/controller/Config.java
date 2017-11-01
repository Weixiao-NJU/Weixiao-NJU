package org.wx.weixiao.controller;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wx.weixiao.Info.ResultMessage;
import org.wx.weixiao.common.LoggerManager;
import org.wx.weixiao.service.AllDispatchCoreService;
import org.wx.weixiao.service.InitMySQL;
import org.wx.weixiao.service.impl.ImportAnswererImpl;
import org.wx.weixiao.util.ApplicationContextHelper;
import org.wx.weixiao.util.ErrorCodeUtil;

import javax.annotation.Resource;

/**
 * Created by Daniel on 2016/12/10.
 */
@RestController
public class Config {

    private final String token = "weixiao2017";

    @Resource(name = "AllDispatchCoreService")
    AllDispatchCoreService allDispatchCoreService;
    @Autowired
    ImportAnswererImpl importAnswerer;


    private static Logger logger = Logger.getLogger(Config.class);


    @RequestMapping("/config")
    public ResultMessage config(@RequestParam(value = "token") String token) {
        ResultMessage resultMessage = new ResultMessage();
        if (this.token.equals(token)) {
            InitMySQL.initSession();
            try {
               InitMySQL.addXMLDatatoMySql();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            InitMySQL.setUpIndex();
            resultMessage.setErrorCode(ErrorCodeUtil.SUCCESS);
            resultMessage.setInfo("Import Answerer successfully.");
            return resultMessage;
        }else{
            resultMessage.setErrorCode(ErrorCodeUtil.CONFIGPERMISSION);
            resultMessage.setInfo("Wrong token");
            return resultMessage;
        }
    }

    @RequestMapping("/importInfos")
    public ResultMessage importInfos(@RequestParam(value = "token") String token, @RequestParam
            (value = "path", defaultValue = "excel/AnswererInfo.xlsx") String path) {
        ResultMessage resultMessage = new ResultMessage();
        if (this.token.equals(token)) {
            importAnswerer.importAnswererInfo(path);
            resultMessage.setErrorCode(ErrorCodeUtil.SUCCESS);
            resultMessage.setInfo("Import Answerer successfully.");
            return resultMessage;
        }else{
            resultMessage.setErrorCode(ErrorCodeUtil.CONFIGPERMISSION);
            resultMessage.setInfo("Wrong token");
            return resultMessage;
        }
    }

    @RequestMapping("/indexData")
    public ResultMessage initIndex(@RequestParam(value = "token") String token){
        ResultMessage resultMessage = new ResultMessage();
        if(!token.equals(token)) {
            resultMessage.setErrorCode(ErrorCodeUtil.CONFIGPERMISSION);
            resultMessage.setInfo("Wrong token");
            return resultMessage;
        }
        ApplicationContext cxt = ApplicationContextHelper.getApplicationContext();
        SessionFactory sessionFactory = (SessionFactory) cxt.getBean("sessionFactory");
        FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.openSession());
        try {
            fullTextSession.createIndexer().startAndWait();
            LoggerManager.info(logger,"Init Data Index");

            resultMessage.setErrorCode(ErrorCodeUtil.SUCCESS);
            resultMessage.setInfo("Data Index init successfully.");
            return resultMessage;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultMessage;
    }

}
