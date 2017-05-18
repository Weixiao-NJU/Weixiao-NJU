<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" type="text/css" href="../css/weui.css" />
    <title>报名结果页</title>
</head>
<body>
    <div class="weui-msg">
        <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
        <div class="weui-msg__text-area">
            <h2 class="weui-msg__title">报名成功</h2>
            <p class="weui-msg__desc">请务必准时参加，我们也会提前提醒您！</p>
        </div>
        <div class="weui-msg__opr-area">
            <p class="weui-btn-area">
                <a onclick="WeixinJSBridge.call('closeWindow');" class="weui-btn weui-btn_primary">关闭</a>
                <a href="/core/lecture?lid=${lid}" class="weui-btn weui-btn_default">返回讲座</a>
            </p>
        </div>

        <div class="weui-msg__extra-area">
            <div class="weui-footer">
                <p class="weui-footer__links">
                    <a href="javascript:void(0);" class="weui-footer__link">学在南大</a>
                </p>
                <p class="weui-footer__text">Copyright &copy; 2016-2017 nju</p>
            </div>
        </div>
    </div>
</body>
</html>
