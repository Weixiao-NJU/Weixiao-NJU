<%@ page import="org.wx.weixiao.util.ServerUtil" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
<%
    String title = "应用配置页";
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
    <link rel="stylesheet" type="text/css" href="<%=resourceUrl%>/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="<%=resourceUrl%>/css/wx.css" />
    <link rel="stylesheet" type="text/css" href="<%=resourceUrl%>/css/pageGroup.css" />
	<link rel="stylesheet" type="text/css" href="<%=resourceUrl%>/css/appConfig.css" />
	<title><%=title%></title>

</head>

<body>
    <div class="area">
        <h1 class="wx-app-type">应用配置页</h1>
    </div>
    <br/>

    <div class="wx-form-group">
        <label  class="wx-control-label"><i>*</i>
            设置问答平台形态
        </label>
        <div class="wx-form-content">
            <label class="wx-radio-content mgr-10"><input type="radio" class="wx-radio" value="official" name="state" checked><div class="wx-radio-style"></div>官方问答平台</label>
            <label class="wx-radio-content mgr-10"><input type="radio" class="wx-radio" value="public"  name="state"><div class="wx-radio-style"></div>开放式问答平台</label>
        </div>
    </div>

    <div class="wx-form-group">
        <label class="wx-control-label"><i>*</i>
            设置工作时间
        </label>
        <div class="wx-form-content">
            <input type="text" class="wx-form-input1" id="time1" name="time1" placeholder="00">
            <sapn>:</sapn>
            <input type="text" class="wx-form-input1" id="time2" name="time2" placeholder="00">
            <label class="wx-control-label1">
                &nbsp;至&nbsp;
            </label>

            <input type="text" class="wx-form-input1" id="time3" name="time3" placeholder="00">
            <sapn>:</sapn>
            <input type="text" class="wx-form-input1" id="time4" name="time4" placeholder="00">
        </div>
    </div>
    <input id="mediaId" style="display: none" value="<%=request.getAttribute("media_id")%>">

    <div class="wx-form-group">
        <button type="submit" class="wx-btn wx-btn-small" onclick="checkForm()">保存</button>
        <button type="submit" class="wx-btn-gray wx-btn-small " onclick="clean()">取消</button>
    </div>

    <div class="area">
        <h1 class="wx-app-type1">文件配置</h1>
    </div>

    <form method="post" enctype="multipart/form-data" id="form_answerer">
        <div class="wx-form-group">
            <label  class="wx-control-label">
                    添加回答者
            </label>
            <div class="wx-form-content">
                <div>
                    <div class="wx-upload">
                        选择文件
                        <input class="wx-upload-input" type="file" value="上传图片" id="wx-upload-answer" name="answerFile" />
                    </div>
                </div>
                <input type="button" class="wx-btn wx-btn-small1" value="上传" onclick="upload_answerer()">
                <label class="upload-tip"><span class="tips"><a href="core/file_download?type=answerer">（点击下载示例表格)</a></span></label>
            </div>
        </div>
    </form>
    <form method="post" enctype="multipart/form-data" id="form_qa">
        <div class="wx-form-group">
            <label class="wx-control-label">
                添加知识库
            </label>
            <div class="wx-form-content">
                <div>
                    <div class="wx-upload">
                        选择文件
                        <input class="wx-upload-input" type="file" value="上传图片" id="wx-upload-qa" name="qaFile"/>
                    </div>
                </div>
                <input type="button" class="wx-btn wx-btn-small1" value="上传" onclick="upload_qa()">
                <label class="upload-tip"><span class="tips"><a href="core/file_download?type=qa">（点击下载示例表格)</a></span></label>
            </div>
        </div>
    </form>
    <br/>

	<div class="wx-menu">
		<ul>
			<li><div onclick="tab('tab1')" class="active" id="answererNow">已有回答者</div></li>
			<li><div onclick="tab('tab2')" id="knowledgeNow">已有知识库</div></li>
            <li><div onclick="tab('tab3')" id="lectureNow">已有讲座库</div></li>
		</ul>
	</div>
    <div id="tab">
        <div id="tab1">
            <div class="wx-form-group">
                <button type="submit" class="wx-btn wx-btn-small addButton" onclick="addAnswerer()">添加回答者</button>
            </div>
            <c:choose>
                <c:when test="${answererInfos.size()==0}">
                    <br>
                    <p style="text-align: center">该公众号目前还没有添加回答者</p>
                    <p style="text-align: center">赶快添加回答者来答疑解惑吧</p>
                    <br>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped table-hover" id="answerer-table">
                        <tr>
                            <th style="text-align:center;">序号</th>
                            <th style="text-align:center;">姓名</th>
                            <th style="text-align:center;">部门</th>
                            <th style="text-align:center;">联系方式</th>
                            <th style="text-align:center;">关键字</th>
                            <th style="text-align:center;">绑定码</th>
                            <th style="text-align:center;" class="table-operation">操作</th>

                        </tr>
                        <% int i=1; %>
                        <c:forEach items="${answererInfos}" var="answererInfo" varStatus="vs">
                            <tr>
                                <td style="text-align:center;" id="${answererInfo.id}"><%=i%></td>
                                <td style="text-align:center;">${answererInfo.getName()}</td>
                                <td style="text-align:center;">${answererInfo.getDepartment()}</td>
                                <td style="text-align:center;">${answererInfo.getTelephone()}</td>
                                <td style="text-align:center;">${answererInfo.getKeyword()}</td>
                                <td style="text-align:center;">${answererInfo.getAccountStr()}</td>
                                <td style="text-align:center;">
                                    <button type="button" class="modify-button" onclick="modifyLine(<%=i%>)">编辑</button>
                                    <button type="button" class="delete-button" onclick="deleteLine(<%=i%>)">删除</button>
                                </td>

                            </tr>
                            <% i++; %>
                        </c:forEach>
                    </table>

                    <input type="hidden" id="start_page">
                    <input type="hidden" id="current_page" />
                    <input type="hidden" id="show_per_page" />
                    <input type="hidden" id="end_page">
                    <!-------------------------------------------分页----------------------------------------------------------------->
                    <div id="pageGro" class="cb">
                        <div class="pagestart">首页</div>
                        <div class="pageUp">上一页</div>
                        <div class="pageList">
                            <ul>
                                <li>1</li>
                                <li>2</li>
                                <li>3</li>
                                <li>4</li>
                                <li>5</li>
                            </ul>
                        </div>
                        <div class="pageDown">下一页</div>
                        <div class="pageend">尾页</div>
                    </div>
                    <!-------------------------------------------END 分页----------------------------------------------------------------->

                </c:otherwise>
            </c:choose>
            <%--编辑栏--%>
            <div class="modify-answer-div" id="0">
                <br>
                <div class="wx-form-group">
                    <label  class="wx-control-label2"><i>*</i>
                        姓名
                    </label>
                    <div class="wx-form-content1">
                        <input type="text" class="wx-form-input2" id="table-div-name" name="name">
                    </div>
                </div>
                <div class="wx-form-group">
                    <label  class="wx-control-label2">
                        部门
                    </label>
                    <div class="wx-form-content1">
                        <input type="text" class="wx-form-input2" id="table-div-department" name="department">
                    </div>
                </div>
                <div class="wx-form-group">
                    <label  class="wx-control-label2">
                        联系方式
                    </label>
                    <div class="wx-form-content1">
                        <input type="text" class="wx-form-input2" id="table-div-tel" name="tel">
                    </div>
                </div>
                <div class="wx-form-group">
                    <label  class="wx-control-label2"><i>*</i>
                        关键字
                    </label>
                    <div class="wx-form-content1">
                        <input type="text" class="wx-form-input2" id="table-div-keyword" name="keyword">
                    </div>
                </div>
                <div class="wx-form-group">
                    <label  class="wx-control-label2">
                        绑定码
                    </label>
                    <div class="wx-form-content1">
                        <input type="text" class="wx-form-input2" id="table-div-accountStr" name="accountString">
                    </div>
                </div>
                <div class="wx-form-group">
                    <button type="submit" class="wx-btn wx-btn-small btn-modify" onclick="saveModify()">保存</button>
                    <button type="submit" class="wx-btn-gray wx-btn-small btn-delete" onclick="cancelModify()">取消</button>
                </div>
            </div>
            <%--添加栏--%>
            <div class="add-answer-div">
                <br>
                <div class="wx-form-group">
                    <label  class="wx-control-label2"><i>*</i>
                        姓名
                    </label>
                    <div class="wx-form-content1">
                        <input type="text" class="wx-form-input2" id="add-table-div-name" name="name">
                    </div>
                </div>
                <div class="wx-form-group">
                    <label  class="wx-control-label2">
                        部门
                    </label>
                    <div class="wx-form-content1">
                        <input type="text" class="wx-form-input2" id="add-table-div-department" name="department">
                    </div>
                </div>
                <div class="wx-form-group">
                    <label  class="wx-control-label2">
                        联系方式
                    </label>
                    <div class="wx-form-content1">
                        <input type="text" class="wx-form-input2" id="add-table-div-tel" name="tel">
                    </div>
                </div>
                <div class="wx-form-group">
                    <label  class="wx-control-label2"><i>*</i>
                        关键字
                    </label>
                    <div class="wx-form-content1">
                        <input type="text" class="wx-form-input2" id="add-table-div-keyword" name="keyword">
                    </div>
                </div>
                <div class="wx-form-group">
                    <label  class="wx-control-label2"><i>*</i>
                        绑定码
                    </label>
                    <div class="wx-form-content1">
                        <input type="text" class="wx-form-input2" id="add-table-div-accountStr" name="accountString">
                    </div>
                </div>
                <div class="wx-form-group">
                    <button type="submit" class="wx-btn wx-btn-small btn-modify" onclick="saveAdd()">添加</button>
                    <button type="submit" class="wx-btn-gray wx-btn-small btn-delete" onclick="cancelAdd()">取消</button>
                </div>
            </div>
        </div>
        <div id="tab2" style="display: none">
            <div class="wx-form-group">
                <button type="submit" class="wx-btn wx-btn-small addButton" onclick="addQA()">添加问答</button>
            </div>
            <c:choose>
                <c:when test="${knowledgeInfos.size()==0}">
                    <br>
                    <p style="text-align: center">该公众号目前还没有添加知识库</p>
                    <p style="text-align: center">赶快添加一些解惑秘诀吧</p>
                    <br>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped table-hover tables" id="qa-table">
                        <tr>
                            <th style="text-align:center;min-width:50px;">序号</th>
                            <th style="text-align:center;" class="table-question">问题</th>
                            <th style="text-align:center;" class="table-answer">回答</th>
                            <th style="text-align:center;min-width:109px;" class="table-operation">操作</th>
                        </tr>
                        <% int j=1; %>
                        <c:forEach items="${knowledgeInfos}" var="knowledgeInfo" varStatus="vs2">
                            <tr>
                                <td style="text-align:center;" id="${knowledgeInfo.id}"><%=j%></td>
                                <td>${knowledgeInfo.getQuestion()}</td>
                                <td>${knowledgeInfo.getAnswer()}</td>
                                <td style="text-align:center;">
                                    <button type="button" class="modify-button" onclick="modifyQALine(<%=j%>)">编辑</button>
                                    <button type="button" class="delete-button" onclick="deleteQALine(<%=j%>)">删除</button>
                                </td>
                            </tr>
                            <% j++; %>
                        </c:forEach>
                    </table>
                    <input type="hidden" id="qa_start_page">
                    <input type="hidden" id="qa_current_page" />
                    <input type="hidden" id="qa_show_per_page" />
                    <input type="hidden" id="qa_end_page">
                    <!-------------------------------------------分页----------------------------------------------------------------->
                    <div id="pageQA" class="cb">
                        <div class="QAPagestart">首页</div>
                        <div class="QAPageUp">上一页</div>
                        <div class="QAPageList">
                            <ul>
                                <li>1</li>
                                <li>2</li>
                                <li>3</li>
                                <li>4</li>
                                <li>5</li>
                            </ul>
                        </div>
                        <div class="QAPageDown">下一页</div>
                        <div class="QAPageend">尾页</div>
                    </div>
                </c:otherwise>
            </c:choose>
            <%--编辑栏--%>
            <div class="modify-qa-div" id="0">
                <br>
                <div class="wx-form-group">
                    <label  class="wx-control-label3"><i>*</i>
                        问题
                    </label>
                    <div class="wx-form-content2">
                        <textarea type="text" class="wx-form-textarea" id="table-div-question" name="question" rows="3" cols="30"></textarea>
                    </div>
                </div>
                <div class="wx-form-group">
                    <label  class="wx-control-label3"><i>*</i>
                        回答
                    </label>
                    <div class="wx-form-content2">
                        <textarea type="text" class="wx-form-textarea" id="table-div-answer" name="answer" rows="4" cols="30"></textarea>
                    </div>
                </div>
                <div class="wx-form-group">
                    <button type="submit" class="wx-btn wx-btn-small btn-modify" onclick="saveQAModify()">保存</button>
                    <button type="submit" class="wx-btn-gray wx-btn-small btn-delete" onclick="cancelQAModify()">取消</button>
                </div>
            </div>
            <%--添加栏--%>
            <div class="add-qa-div">
                <br>
                <div class="wx-form-group">
                    <label  class="wx-control-label3"><i>*</i>
                        问题
                    </label>
                    <div class="wx-form-content2">
                        <textarea type="text" class="wx-form-textarea" id="add-table-div-question" name="question" rows="3" cols="30"></textarea>
                    </div>
                </div>
                <div class="wx-form-group">
                    <label  class="wx-control-label3"><i>*</i>
                        回答
                    </label>
                    <div class="wx-form-content2">
                        <textarea type="text" class="wx-form-textarea" id="add-table-div-answer" name="answer" rows="4" cols="30"></textarea>
                    </div>
                </div>

                <div class="wx-form-group">
                    <button type="submit" class="wx-btn wx-btn-small btn-modify" onclick="saveQAAdd()">添加</button>
                    <button type="submit" class="wx-btn-gray wx-btn-small btn-delete" onclick="cancelQAAdd()">取消</button>
                </div>
            </div>
        </div>
        <div id="tab3" style="display: none">

            <c:choose>
                <c:when test="${knowledgeInfos.size()==0}">
                    <br>
                    <p style="text-align: center">该公众号目前还没有添加知识库</p>
                    <p style="text-align: center">赶快添加一些解惑秘诀吧</p>
                    <br>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped table-hover tables" id="lecture-table">
                        <tr>
                            <th style="text-align:center;min-width:50px;">序号</th>
                            <th style="text-align:center;">主题</th>
                            <th style="text-align:center;min-width:100px;">主讲</th>
                            <th style="text-align:center;">院系</th>
                            <th style="text-align:center; width: 35%;">详情</th>
                            <th style="text-align:center; width: 120px;">时间</th>
                            <th style="text-align:center;min-width:80px;">地点</th>
                            <th style="text-align:center;min-width:80px;">报名人数</th>
                            <th style="text-align:center;min-width:100px;">感兴趣人数</th>
                        </tr>
                        <% int z=1; %>
                        <c:forEach items="${lectureInfos}" var="lecture" varStatus="vs3">
                            <tr>
                                <td style="text-align:center;"><%=z%></td>
                                <td>${lecture.title}</td>
                                <td style="text-align:center;">${lecture.teacher}</td>
                                <td style="text-align:center;">${lecture.academy}</td>
                                <td>${lecture.introduction}</td>
                                <td>${fn:substring(lecture.startTime, 0, 16)} - ${fn:substring(lecture.endTime, 11, 16)}</td>
                                <td style="text-align:center;">${lecture.place}</td>
                                <td style="text-align:center;">${lecture.subscriberNum}</td>
                                <td style="text-align:center;">${lecture.interestNum}</td>
                            </tr>
                            <% z++; %>
                        </c:forEach>
                    </table>
                    <input type="hidden" id="lecture_num_page" value="${pageNum}">
                    <input type="hidden" id="lecture_start_page">
                    <input type="hidden" id="lecture_current_page" />
                    <input type="hidden" id="lecture_show_per_page" />
                    <input type="hidden" id="lecture_end_page">

                    <div id="loading">
                        <img src="<%=resourceUrl%>/image/loading.gif">
                    </div>
                    <br>
                    <!-------------------------------------------分页----------------------------------------------------------------->
                    <div id="pageLecture" class="cb">
                        <div class="LPagestart">首页</div>
                        <div class="LPageUp">上一页</div>
                        <div class="LPageList">
                            <ul>
                                <li>1</li>
                                <li>2</li>
                                <li>3</li>
                                <li>4</li>
                                <li>5</li>
                            </ul>
                        </div>
                        <div class="LPageDown">下一页</div>
                        <div class="LPageend">尾页</div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>



</body>
<script src="<%=resourceUrl%>/js/jquery-3.1.1.min.js"></script>
<script src="<%=resourceUrl%>/js/jquery.form.js"></script>
<script src="<%=resourceUrl%>/js/pageGroup.js"></script>
<script src="<%=resourceUrl%>/js/pageGroup-1.js"></script>
<script src="<%=resourceUrl%>/js/pageGroup-2.js"></script>
<script src="<%=resourceUrl%>/js/appConfig.js"></script>
<script>
    function tab(pid){
        var tabs=["tab1","tab2","tab3"];
        for(var i=0;i<3;i++){
            if (tabs[i] == pid) {
                document.getElementById(tabs[i]).style.display = "block";
                if (i == 0) {
                    $("#knowledgeNow").removeClass("active");
                    $("#lectureNow").removeClass("active");
                    $("#answererNow").addClass("active");
                } else if(i==1){
                    $("#answererNow").removeClass("active");
                    $("#lectureNow").removeClass("active");
                    $("#knowledgeNow").addClass("active");
                } else{
                    $("#answererNow").removeClass("active");
                    $("#knowledgeNow").removeClass("active");
                    $("#lectureNow").addClass("active");
                }
            }else {
                document.getElementById(tabs[i]).style.display = "none";
            }
        }

    }
    function modifyLine(i){

        var name = $("#answerer-table").find("tr").eq(i).find("td").eq(1).text();
        var department = $("#answerer-table").find("tr").eq(i).find("td").eq(2).text();
        var tel = $("#answerer-table").find("tr").eq(i).find("td").eq(3).text();
        var keyword = $("#answerer-table").find("tr").eq(i).find("td").eq(4).text();
        var accountStr = $("#answerer-table").find("tr").eq(i).find("td").eq(5).text();

        department = department!='/'?department:"";
        tel = tel!='/'?tel:"";
        accountStr = accountStr!='已绑定'?accountStr:"";

        //keyword处理
        var length=keyword.length;
        keyword=keyword.substring(1,length-1);

        $("#table-div-name").val(name);
        $("#table-div-department").val(department);
        $("#table-div-tel").val(tel);
        $("#table-div-keyword").val(keyword);
        $("#table-div-accountStr").val(accountStr);
        $(".modify-answer-div").attr("id",i);
        $(".add-answer-div").css("display","none");
        $(".modify-answer-div").css("display","block");
        event.stopPropagation();

    }

    function addAnswerer(){
        $("#add-table-div-name").val("");
        $("#add-table-div-department").val("");
        $("#add-table-div-tel").val("");
        $("#add-table-div-keyword").val("");
        $("#add-table-div-accountStr").val("");
        $(".modify-answer-div").css("display","none");
        $(".add-answer-div").css("display","block");
        event.stopPropagation();
    }

    function deleteLine(i){
        $(".modify-answer-div").css("display","none");
        var id = $("#answerer-table").find("tr").eq(i).find("td").eq(0).attr('id');
        var res = confirm('确认要删除吗？');
        if(res == true) {
            deleting(i,id);
        }
    }

    function modifyQALine(i){

        var question = $("#qa-table").find("tr").eq(i).find("td").eq(1).text();
        var answer = $("#qa-table").find("tr").eq(i).find("td").eq(2).text();


        $("#table-div-question").val(question);
        $("#table-div-answer").val(answer);

        $(".modify-qa-div").attr("id",i);
        $(".add-qa-div").css("display","none");
        $(".modify-qa-div").css("display","block");
        event.stopPropagation();

    }

    function addQA(){
        $("#add-table-div-question").val("");
        $("#add-table-div-answer").val("");
        $(".modify-qa-div").css("display","none");
        $(".add-qa-div").css("display","block");
        event.stopPropagation();
    }

    function deleteQALine(i){
        $(".modify-qa-div").css("display","none");
        var id = $("#qa-table").find("tr").eq(i).find("td").eq(0).attr('id');
        var res = confirm('确认要删除吗？');
        if(res == true) {
            deleteQA(i,id);
        }
    }
</script>
</html>