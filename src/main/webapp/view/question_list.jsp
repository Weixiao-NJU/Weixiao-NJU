<%@ page import="org.wx.weixiao.util.ServerUtil" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>

    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + ServerUtil.RESOURCE_ADDRESS + ":" + request.getServerPort()
                + path + "/";
    %>
    <base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<link rel="stylesheet" type="text/css" href="../css/weui.css" />
    <link rel="stylesheet" type="text/css" href="../css/list.css">
	<title>信息页</title>
</head>

<body>
	<div class="header">
		<p class="header-p">${title}</p>
	</div>

    <c:choose>
        <c:when test="${list.size()==0}">
            <br><br><br><br>

            <p style="text-align: center">${info}</p>
            <br>
            <div class="weui-footer  weui-footer_fixed-bottom">
                <p class="weui-footer__links">
                    <a href="javascript: void(0)" class="weui-footer__link">学在南大</a>
                </p>
                <p class="weui-footer__text">Copyright &copy; 2016-2017 nju</p>
            </div>
        </c:when>

        <c:otherwise>
            <c:forEach items="${list}" var="info" varStatus="vs">
                <div class="weui-cells__title">${info.createAt}</div>
                <div class="weui-cells">
                    <c:choose>
                        <c:when test="${type.toString()=='UQ'}">
                            <a class="weui-cell weui-cell_access" href="answer_question?encrypt=${info.id}">
                        </c:when>
                        <c:when test="${type.toString()=='ONDUTY'}">
                            <a class="weui-cell weui-cell_access" href="answer_question?encrypt=${info.id}">
                        </c:when>
                        <c:otherwise>
                        <a class="weui-cell weui-cell_access" href="get_reply?encrypt=${info.id}">
                        </c:otherwise>
                    </c:choose>
                        <div class="weui-cell__hd">
                            <img src="../image/question.svg" alt="" style="width:20px;margin-right:5px;display:block">
                        </div>
                        <div class="weui-cell__bd weui-cell-bd-text">
                            <p>问题：</p>
                        </div>
                        <div class="weui-cell__ft  weui-title-right">${info.content}</div>
                    </a>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <img src="../image/key.svg" alt="" style="width:20px;margin-right:5px;display:block">
                        </div>
                        <div class="weui-cell__bd">
                            <p>关键字：</p>
                        </div>
                        <div class="weui-cell__ft">${info.keyword}</div>
                    </div>
                </div>
            </c:forEach>

            <br><br><br><br>
            <div class="weui-footer ">
                <p class="weui-footer__links">
                    <a href="javascript: void(0)" class="weui-footer__link">学在南大</a>
                </p>
                <p class="weui-footer__text">Copyright &copy; 2016-2017 nju</p>
            </div>
        </c:otherwise>
    </c:choose>

</body>

</html>