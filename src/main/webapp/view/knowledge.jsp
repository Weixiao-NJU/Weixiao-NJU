<%@ page import="org.wx.weixiao.Info.KnowledgeInfo" %>
<%@ page import="org.wx.weixiao.util.ServerUtil" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<%
    String title = "知识库";
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
    <link rel="stylesheet" type="text/css" href="../css/knowledge.css" />
    <title><%=title%></title>
</head>

<body>
	<%
		KnowledgeInfo info = (KnowledgeInfo) request.getAttribute("knowledge");
	%>


    <div id="div2">
        <div>
            <p class="content"><span>问题： </span><%=info.getQuestion()%></p>
        </div>
        <div>
            <p class="content"><span>回答： </span><%=info.getAnswer()%></p>
        </div>

	</div>
    <div class="weui-msg__extra-area">
        <div class="weui-footer">
            <p class="weui-footer__links">
                <a href="javascript:void(0);" class="weui-footer__link">学在南大</a>
            </p>
            <p class="weui-footer__text">Copyright &copy; 2016-2017 nju</p>
        </div>
    </div>
</body>
</html>