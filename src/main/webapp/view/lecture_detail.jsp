<%@ page import="org.wx.weixiao.util.ServerUtil" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
<%
    String title = "讲座详情";
%>
<head>
    <%
        String resourceUrl = "http://"+ ServerUtil.RESOURCE_ADDRESS;
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + ServerUtil.RESOURCE_ADDRESS + ":" + request.getServerPort()
                + path + "/";
    %>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" type="text/css" href="<%=resourceUrl%>/css/weui.css" />
    <link rel="stylesheet" type="text/css" href="<%=resourceUrl%>/css/lecture.css" />
    <link rel="stylesheet" type="text/css" href="<%=resourceUrl%>/css/lecture_detail.css" />
    <title><%=title%></title>
</head>
<body>
    <div id="wrap">
        <div id="main" class="clearfix">
            <div class="weui-panel weui-panel_access">
                <div class="weui-panel__hd">讲座详情</div>

                <div class="weui-panel__bd">

                    <div  class="weui-media-box weui-media-box_text">
                        <div class="weui-media-box__bd">
                            <h4 class="weui-media-box__title">${detail.title}</h4>

                            <p class="weui-media-box__desc">主讲：${detail.teacher}</p>
                            <p class="weui-media-box__desc">院系：${detail.academy}</p>
                            <p class="weui-media-box__desc">时间：${fn:substring(detail.startTime, 0, 16)} - ${fn:substring(detail.endTime, 11, 16)}</p>
                            <p class="weui-media-box__desc">地点：${detail.place}</p>
                            <p class="weui-media-box__desc description">描述：${detail.introduction}</p>

                            <p class="lecture-interest-num-p">当前已有 <span class="lecture-interest-num">${detail.interestNum}</span>
                                人表示感兴趣！
                                <c:choose>
                                    <c:when test="${isInterest}">
                                        <img src="<%=resourceUrl%>/image/loved.svg" class="interest-button">
                                    </c:when>
                                    <c:otherwise>
                                        <img onclick="interest()" src="<%=resourceUrl%>/image/love.svg" class="interest-button">
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="buttons">
                <br>
                <c:choose>
                    <c:when test="${isSubscriber}">
                        <a href="javascript:;" class="weui-btn weui-btn_disabled weui-btn_default">已报名</a>
                    </c:when>
                    <c:otherwise>
                        <a href="core/join_lecture?lid=${detail.lid}" class="weui-btn weui-btn_primary">报名参加</a>
                    </c:otherwise>
                </c:choose>
            </div>
            <input id="lid" type="hidden" value="${detail.lid}">
            <br>
            <br>
        </div>
    </div>

    <div class="weui-footer weui-footer_fixed-bottom" id="footer">
        <p class="weui-footer__links">
            <a href="javascript: void(0)" class="weui-footer__link">学在南大</a>
        </p>
        <p class="weui-footer__text">Copyright &copy; 2016-2017 nju</p>
    </div>
</body>
<script src="<%=resourceUrl%>/js/jquery-3.1.1.min.js"></script>
<script src="<%=resourceUrl%>/js/lecture.js"></script>
</html>
