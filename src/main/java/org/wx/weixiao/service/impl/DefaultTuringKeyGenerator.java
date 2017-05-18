package org.wx.weixiao.service.impl;

import org.springframework.stereotype.Component;
import org.wx.weixiao.exception.turingrobot.KeyGeneratingException;
import org.wx.weixiao.util.turingapi.TuringKeyGenerator;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Daniel on 2017/4/26.
 * 默认的key生成器
 * 后期可能会使用多个key
 */
@Component
public class DefaultTuringKeyGenerator implements TuringKeyGenerator {
    private String key;
    private static final String RESOURCES_FILE_NAME = "turingkey.properties";

    @Override
    public String getKey(String userId) throws KeyGeneratingException {
        if (key == null) {
            Properties properties = new Properties();
            try {
                properties.load(getClass().getResourceAsStream("/" + RESOURCES_FILE_NAME));
                key = (String) properties.get("key");
            } catch (IOException e) {
                e.printStackTrace();
                throw new KeyGeneratingException("turing key文件加载失败，确认资源目录下有" + RESOURCES_FILE_NAME + "文件");
            }
        }
        return key;
    }
}
