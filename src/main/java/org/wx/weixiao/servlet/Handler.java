package org.wx.weixiao.servlet;


import org.wx.weixiao.servlet.conf.AppConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Handler {
    void handle(HttpServletRequest request, HttpServletResponse response, AppConfig
            config) throws ServletException, IOException;
}