package org.wx.weixiao.util;

import org.wx.weixiao.servlet.conf.AppConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jerry Wang on 2016/12/31.
 */
public class AppConfigUtil {


    public final static String ALLDISPATCH = "Alldispatch", H5 = "H5";

    private static Map<String, AppConfig> configMap = new HashMap<>();

    public static void put(String appName, AppConfig appConfig) {
        configMap.put(appName, appConfig);
    }

    public static AppConfig get(String appName) {
        AppConfig appConfig = configMap.get(appName);

        //测试的时候由于没有初始化
        if (appConfig==null) {
            appConfig = new AppConfig();
            appConfig.setApiKey("6D951A0B1002B090");
            appConfig.setApiSecret("60F270415B0B41987C30A7CF12C86FE0");
            appConfig.setToken("mytoken");
            configMap.put(appName, appConfig);
        }
        return appConfig;
    }
}
