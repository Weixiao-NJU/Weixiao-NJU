<%@ page import="org.wx.weixiao.util.ServerUtil" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
<head>
<%
    String resourceUrl = "http://"+ ServerUtil.RESOURCE_ADDRESS;
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" type="text/css" href="<%=resourceUrl%>/css/weui.css" />
    <link rel="stylesheet" type="text/css" href="<%=resourceUrl%>/css/lecture.css" />
    <link rel="stylesheet" type="text/css" href="<%=resourceUrl%>/css/lectures.css" />
    <title>${title}</title>
</head>
<body><c:choose>
    <c:when test="${list.size()==0}">
        <br><br><br><br>
        <p style="text-align: center">当前没有${title}！</p>
        <br>
    </c:when>

    <c:otherwise>
        <div class="weui-panel weui-panel_access">
            <div class="weui-panel__hd">${title}</div>
            <div class="weui-panel__bd">
                <c:forEach var="lecture" items="${list}">
                    <a href="/core/lecture?lid=${lecture.lid}" class="weui-media-box weui-media-box_appmsg">
                        <div class="weui-media-box__hd">
                            <div class="lecture-img-div">
                                <img class="weui-media-box__thumb" src="<%=resourceUrl%>/image/lecture.svg" alt="">
                            </div>
                        </div>
                        <div class="weui-media-box__bd">
                            <h4 class="weui-media-box__title">${lecture.title}</h4>
                            <p class="weui-media-box__desc">主讲：${lecture.teacher}--${lecture.academy}</p>
                            <p class="weui-media-box__desc">时间：${fn:substring(lecture.startTime, 0, 16)} - ${fn:substring(lecture.endTime, 11, 16)}</p>
                            <p class="weui-media-box__desc">地点：${lecture.place}</p>
                        </div>
                    </a>
                </c:forEach>
            </div>
            <c:if test="${type == 1}">
                <div class="weui-panel__ft">
                    <a href="javascript:void(0);" class="weui-cell weui-cell_access weui-cell_link">
                        <div class="weui-cell__bd" onclick="loadMore()">查看更多</div>
                        <span class="weui-cell__ft"></span>
                    </a>
                </div>
            </c:if>
        </div>
    </c:otherwise>
    </c:choose>

    <div id="loadingToast" style="display:none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">数据加载中</p>
        </div>
    </div>
    <input type="hidden"  id="pageNum" value="1">
    <br>
    <br>
    <br>
    <div id="msg-div" style="display: none;">
        <div class="weui-mask"></div>
        <div class="weui-dialog">
            <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示信息</strong></div>
            <div class="weui-dialog__bd">没有更多讲座信息了!</div>
            <div class="weui-dialog__ft">
                <a onclick="ok()" class="weui-dialog__btn weui-dialog__btn_primary">确定</a>
            </div>
        </div>
    </div>
    <div class="weui-footer weui-footer_fixed-bottom">
        <p class="weui-footer__links">
            <a href="javascript: void(0)" class="weui-footer__link">学在南大</a>
        </p>
        <p class="weui-footer__text">Copyright &copy; 2016-2017 nju</p>
    </div>
</body>
<script src="<%=resourceUrl%>/js/jquery-3.1.1.min.js"></script>
<script src="<%=resourceUrl%>/js/all_lectures.js"></script>
</html>
