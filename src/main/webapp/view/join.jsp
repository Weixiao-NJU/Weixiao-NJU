<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<%
    String title = "参加讲座";
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
	<link rel="stylesheet" type="text/css" href="../css/question.css" />
	<title><%=title%></title>
</head>

<body>
	<form name="form" method="post" action="core/submit_join" accept-charset="utf-8">
        <div class="weui-cells__title">第一步：请输入您的邮箱</div>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<textarea class="weui-textarea" name="email" rows="1" required></textarea>
				</div>
			</div>
		</div>

        <div class="weui-cells__title">第二步：请输入您的电话</div>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<textarea class="weui-textarea" name="phone" rows="1" required></textarea>
				</div>
			</div>
		</div>
		<br>
		<input name="lid" style="display: none" value="${lid}">
		<input type="submit" class="weui-btn weui-btn_primary"  value="报名参加">
	</form>

	<div class="weui-footer weui-footer_fixed-bottom">
		<p class="weui-footer__links">
			<a href="javascript: void(0)" class="weui-footer__link">学在南大</a>
		</p>
		<p class="weui-footer__text">Copyright &copy; 2016-2017 nju</p>
	</div>
</body>
<script src="../js/jquery-3.1.1.min.js"></script>
</html>