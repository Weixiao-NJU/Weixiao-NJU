<%@ page import="org.wx.weixiao.util.ServerUtil" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<%
    String title = "问题提交页";
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
	<link rel="stylesheet" type="text/css" href="../css/question.css" />
	<title><%=title%></title>
</head>

<body>
	<form name="form" method="post" action="core/submit_question" accept-charset="utf-8">
        <div class="weui-cells__title">第一步：请输入您的问题</div>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<textarea class="weui-textarea" name="question" placeholder="开始输入..." rows="5" required></textarea>
					<div class="weui-textarea-counter"><span>0</span>/200</div>
				</div>
			</div>
		</div>

        <div class="weui-cells__title">第二步：请选择回答者关键字</div>
        <div class="weui-cells">
            <div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__bd">
                    <select class="weui-select" name="keyword">
						<c:forEach items="${k_list}" var="keyInfo" varStatus="vs">
                        	<option value="${keyInfo.getKeyword()}">${keyInfo.getKeyword()}</option>
						</c:forEach>
                    </select>
                </div>
            </div>
		</div>
		<br>
		<div class="weui-cells__title">在提问之前可以尝试使用公众号的智能问答系统更快速便捷寻找答案</div>
		<input name="mediaId" style="display: none" value="<%=request.getAttribute("media_id")%>">
		<input name="openId" style="display: none" value="<%=request.getAttribute("open_id")%>">
		<input type="submit" class="weui-btn weui-btn_primary"  value="提交问题">
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