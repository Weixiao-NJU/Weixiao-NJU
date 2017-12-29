<%@ page import="org.wx.weixiao.Info.QuestionInfo" %>
<%@ page import="org.wx.weixiao.util.ServerUtil" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<%
    String title = "回执页";
%>
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
    <link rel="stylesheet" type="text/css" href="../css/reply.css" />
	<title><%=title%></title>
</head>

<body>
	<%
		QuestionInfo question = (QuestionInfo) request.getAttribute("question");
	%>
    <div class="weui-cells__title">问题详情</div>
    <div class="weui-cells">
        <div class="weui-cell1">
            <div class="cell_question_q">
                <img src="../image/question.svg" alt="" style="width:20px;margin-right:5px;display:block">
                <p class="q_title">问题：</p>
            </div>
            <div class="questionContent"><%=question.getContent()%></div>
        </div>
        <div class="weui-cell" style="display: none">
            <div class="weui-cell__hd"><img src="../image/people.svg" alt="" style="width:20px;margin-right:5px;display:block"></div>
            <div class="weui-cell__bd">
                <p>提问者：</p>
            </div>
            <div class="weui-cell__ft"><%=question.getCreateUser()%></div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><img src="../image/time.svg" alt="" style="width:20px;margin-right:5px;display:block"></div>
            <div class="weui-cell__bd">
                <p>提问时间：</p>
            </div>
            <div class="weui-cell__ft"><%=question.getCreateAt()%></div>
        </div>
    </div>
    <br>

    <div class="weui-cells__title">回答列表</div>
    <c:choose>
        <c:when test="${answers.size()==0}">
            <br>
            <p style="text-align: center">该问题暂时没有被回答哟！</p>
            <br>
            <div class="weui-footer  weui-footer_fixed-bottom">
                <p class="weui-footer__links">
                    <a href="javascript: void(0)" class="weui-footer__link">学在南大</a>
                </p>
                <p class="weui-footer__text">Copyright &copy; 2016-2017 nju</p>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${answers}" var="info" varStatus="vs">
                <div class="weui-cells">
                    <div class="weui-cell1">
                        <div class="cell_question_q">
                            <img src="../image/answer.svg" alt="" style="width:20px;margin-right:5px;display:block">
                            <p class="q_title">回答：</p>
                        </div>
                        <div class="questionContent">${info.getContent()}</div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><img src="../image/people.svg" alt="" style="width:20px;margin-right:5px;display:block"></div>
                        <div class="weui-cell__bd">
                            <p>回答者：</p>
                        </div>
                        <div class="weui-cell__ft">${info.getAnswererName()}</div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><img src="../image/time.svg" alt="" style="width:20px;margin-right:5px;display:block"></div>
                        <div class="weui-cell__bd">
                            <p>回答时间：</p>
                        </div>
                        <div class="weui-cell__ft">${info.getAnswerTime()}</div>
                    </div>
                </div>
            </c:forEach>
            <br><br><br><br>
            <div class="weui-footer">
                <p class="weui-footer__links">
                    <a href="Javascript: void(0)" class="weui-footer__link">学在南大</a>
                </p>
                <p class="weui-footer__text">Copyright &copy; 2016-2017 nju</p>
            </div>
        </c:otherwise>
    </c:choose>
</body>
</html>