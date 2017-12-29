<%@ page import="org.wx.weixiao.Info.QuestionInfo" %>
<%@ page import="org.wx.weixiao.util.ServerUtil" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<%
    String title = "回答页";
%>
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
    <link rel="stylesheet" type="text/css" href="<%=resourceUrl%>/css/answer.css" />
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
                <img src="<%=resourceUrl%>/image/question.svg" alt="" style="width:20px;margin-right:5px;display:block">
                <p class="q_title">问题：</p>
            </div>
            <div id="questionContent"><%=question.getContent()%></div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><img src="<%=resourceUrl%>/image/people.svg" alt="" style="width:20px;margin-right:5px;display:block"></div>
            <div class="weui-cell__bd">
                <p>提问者：</p>
            </div>
            <div class="weui-cell__ft"><%=question.getCreateUser()%></div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><img src="<%=resourceUrl%>/image/time.svg" alt="" style="width:20px;margin-right:5px;display:block"></div>
            <div class="weui-cell__bd">
                <p>提问时间：</p>
            </div>
            <div class="weui-cell__ft"><%=question.getCreateAt()%></div>
        </div>
    </div>

    <br>
    <form name="form" class="answer_form" method="post" action="core/submit_answer" accept-charset="utf-8">
        <div class="weui-cells__title">第一步：请输入您的回答</div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <textarea class="weui-textarea" name="answer" placeholder="开始输入..." rows="3" required></textarea>
                    <div class="weui-textarea-counter"><span>0</span>/200</div>
                </div>
            </div>
        </div>

        <div class="weui-cells__title">第二步：请选择是否公开此次问答</div>
        <div class="weui-cells">
            <div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__bd">
                    <select class="weui-select" name="power">
                        <option value="private">不公开</option>
                        <option value="public">公开</option>
                    </select>
                </div>
            </div>
        </div>
        <br>
        <input name="questionId" style="display: none" value="<%=request.getAttribute("question_id")%>">
        <input name="answererId" style="display: none" value="<%=request.getAttribute("answerer_id")%>">
        <input type="submit" class="weui-btn weui-btn_primary"  value="提交回答">
    </form>
    <div id="func_div">
        <span style="float: left">无法回答？</span>
        <a style="float: right" href="core/forward" class="forward">转发此问题</a>
    </div>
    <br><br><br><br>
	<div class="weui-footer">
		<p class="weui-footer__links">
			<a href="javascript: void(0)" class="weui-footer__link">学在南大</a>
		</p>
		<p class="weui-footer__text">Copyright &copy; 2016-2017 nju</p>
	</div>
</body>
<script src="<%=resourceUrl%>/js/jquery-3.1.1.min.js"></script>
<script>
    $("textarea").focus(function(){$(".weui-footer").hide();});
    $("textarea").blur(function(){$(".weui-footer").show();});
</script>
</html>