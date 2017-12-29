<%@ page import="org.wx.weixiao.util.ServerUtil" %><%--
  Created by IntelliJ IDEA.
  User: wangjiawei
  Date: 2017/1/3
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="utf-8" %>
<html>
<head>
    <%
        String resourceUrl = "http://"+ ServerUtil.RESOURCE_ADDRESS;
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" type="text/css" href="<%=resourceUrl%>/css/weui.css" />
    <title>问题回答</title>
</head>
<body>
<div class="weui-msg">
    <div class="weui-msg__icon-area"><i class="weui-icon-info weui-icon_msg"></i></div>
    <div class="weui-msg__text-area">
        <h2 class="weui-msg__title">您已回答过该问题</h2>
        <p class="weui-msg__desc">是否继续回答该问题</p>
    </div>
    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
            <a href="<%=request.getAttribute("url")%>" class="weui-btn weui-btn_primary">继续回答该问题</a>
            <a onclick="WeixinJSBridge.call('closeWindow');" class="weui-btn weui-btn_default">返回</a>
        </p>
    </div>
    <div class="weui-msg__extra-area">
        <div class="weui-footer">
            <p class="weui-footer__links">
                <a href="javascript:void(0);" class="weui-footer__link">学在南大</a>
            </p>
            <p class="weui-footer__text">Copyright &copy; 2016-2017 nju</p>
        </div>
    </div>
</div>
</body>
</html>
