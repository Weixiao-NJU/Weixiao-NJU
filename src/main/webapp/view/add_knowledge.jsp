<%@ page import="org.wx.weixiao.util.ServerUtil" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<%
    String title = "添加知识库";
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
	<title><%=title%></title>
</head>

<body>
	<form name="form" method="post" action="core/sumbit_knowledge" accept-charset="utf-8">
        <div class="weui-cells__title">添加的问题（可编辑）</div>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<textarea class="weui-textarea"  name="new_question" placeholder="开始输入..." rows="3" required>${question}</textarea>
					<div class="weui-textarea-counter"><span>0</span>/200</div>
				</div>
			</div>
		</div>

		<div class="weui-cells__title">对应的回答（可编辑）</div>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<textarea class="weui-textarea"  name="new_answer" placeholder="开始输入..." rows="3" required>${answer}</textarea>
					<div class="weui-textarea-counter"><span>0</span>/200</div>
				</div>
			</div>
		</div>
		<br>
		<input type="submit" class="weui-btn weui-btn_primary"  value="确认添加">
	</form>

	<div class="weui-footer weui-footer_fixed-bottom">
		<p class="weui-footer__links">
			<a href="javascript: void(0)" class="weui-footer__link">学在南大</a>
		</p>
		<p class="weui-footer__text">Copyright &copy; 2016-2017 nju</p>
	</div>
</body>
<script src="../js/jquery-3.1.1.min.js"></script>
<script>
    $("textarea").focus(function(){$(".weui-footer").hide();});
    $("textarea").blur(function(){$(".weui-footer").show();});
</script>
</html>