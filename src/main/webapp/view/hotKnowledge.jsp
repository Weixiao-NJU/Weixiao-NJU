<%--
  Created by IntelliJ IDEA.
  User: wangjiawei
  Date: 2017/4/30
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" type="text/css" href="../css/weui.css"/>
    <link rel="stylesheet" type="text/css" href="../css/list.css">
    <title>热门问答列表</title>
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
            <div class="weui-cells">

                <a class="weui-cell weui-cell_access" href="knowledgeBase?id=${info.id}">
                    <div class="weui-cell__hd">
                        <img src="../image/knowledge.svg" alt="" style="width:20px;margin-right:5px;display:block"></div>
                    <div class="weui-cell__bd  weui-cell-bd-text">
                        <p>问题：</p>
                    </div>
                    <div class="weui-cell__ft  weui-title-right">${info.question}</div>
                </a>
                <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <img src="../image/hot.svg" alt="" style="width:20px;margin-right:5px;display:block"></div>
                    <div class="weui-cell__bd">
                        <p>热度：</p>
                    </div>
                    <div class="weui-cell__ft">${info.heat}</div>
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