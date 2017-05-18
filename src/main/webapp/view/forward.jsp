<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<%
    String title ="转发页";
%>
<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" type="text/css" href="../css/weui.css" />
	<title><%=title%></title>
</head>

<body>
    <form name="form" class="answer_form" method="post" action="core/submit_forward" accept-charset="utf-8">
        <div class="weui-cells__title">请选择将该问题转发给哪位老师</div>
        <div class="weui-cells">
            <div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__bd">
                    <select class="weui-select" name="teachers">
                        <c:forEach items="${answerers}" var="teacher" varStatus="vs">
                            <option value="${teacher.getId()}">${teacher.getName()}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <br>
        <input type="submit" class="weui-btn weui-btn_primary"  value="确认选择">
    </form>

    <br><br><br><br>
	<div class="weui-footer weui-footer_fixed-bottom">
		<p class="weui-footer__links">
			<a href="javascript: void(0)" class="weui-footer__link">学在南大</a>
		</p>
		<p class="weui-footer__text">Copyright &copy; 2016-2017 nju</p>
	</div>
</body>
</html>