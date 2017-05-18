    $(document).ready(function(){
        init();
        var rows = $("#answerer-table").find("tr").length;
        var colums = $("#answerer-table").find("tr").eq(1).find("td").length-1;
        for(var i=1;i<rows;i++){
            for(var j=0;j<colums;j++){
                var value=$("#answerer-table").find("tr").eq(i).find("td").eq(j).text();
                if(value==""){
                    $("#answerer-table").find("tr").eq(i).find("td").eq(j).text("/")
                    if(j==5){
                        $("#answerer-table").find("tr").eq(i).find("td").eq(j).text("已绑定")
                    }
                }
            }
        }
        //关闭编辑区
        $(document).click(function(){
            $(".modify-answer-div").css("display","none");
            $(".add-answer-div").css("display","none");
            $(".modify-qa-div").css("display","none");
            $(".add-qa-div").css("display","none");
        });
        $(".modify-answer-div").click(function(){
            event.stopPropagation();
        });
        $(".add-answer-div").click(function(){
            event.stopPropagation();
        });
        $(".modify-qa-div").click(function(){
            event.stopPropagation();
        });
        $(".add-qa-div").click(function(){
            event.stopPropagation();
        });
    });
    //提交常用配置
    function checkForm() {
        var mstate = $("input:radio[name='state']:checked").val();
        var mtime1 = $("#time1").val();
        var mtime2 = $("#time2").val();
        var mtime3 = $("#time3").val();
        var mtime4 = $("#time4").val();
        var media_id = $("#mediaId").val();

        var reg = /^[0-9]*$/;

        if(mtime1==""||mtime2==""||mtime3==""||mtime4==""){
            alert("时间没输完哦！");
            return false;
        }
        if(!reg.test(mtime1)||!reg.test(mtime2)||!reg.test(mtime3)||!reg.test(mtime4)){
            alert("非法字符！");
            return false;
        }
        if(mtime1>23||mtime2>59||mtime3>23||mtime4>59){
            alert("时间格式不正确！");
            return false;
        }
        var time1 = mtime1+":"+mtime2;
        var time2 = mtime3+":"+mtime4;

        $.ajax({
            type: "post",
            async: true,
            url: "/core/submit_config",
            data: {
                "time1": time1,
                "time2": time2,
                "media_id":media_id,
                "state": mstate
            },
            success: function (result) {
                if (result == "success") {
                    alert('提交成功');
                } else {
                    alert(result);
                }
            },
            error: function () {
                alert("出故障了请稍候再试");
            }
        })

        return true;
    }

    //取消此次常用配置修改
    function clean() {
        //还原用户设置状态
        init();
        $("input:radio[name='state']")[0].checked="checked";
        // $("#time1").val("");
        // $("#time2").val("");
        // $("#time3").val("");
        // $("#time4").val("");
    }

    //设置为系统存储配置
    function init(){
        var media_id = $("#mediaId").val();
        $.ajax({
            type: "post",
            async: true,
            url: "/core/get_time",
            data: {
                "media_id":media_id
            },
            success: function (time) {
                var result=time.split(":");
                $("#time1").val(result[0]);
                $("#time2").val(result[1]);
                $("#time3").val(result[2]);
                $("#time4").val(result[3]);
            },
            error: function () {
                alert("出故障了请稍候再试");
            }
        })

    }
    //保存修改回答者
    function saveModify(){
        var i =$(".modify-answer-div").attr("id");
        var id = $("#answerer-table").find("tr").eq(i).find("td").eq(0).attr('id');
        var mediaId = $("#mediaId").val();
        //输入检测
        var name = $("#table-div-name").val();
        var department = $("#table-div-department").val();
        var tel = $("#table-div-tel").val();
        var keyword = $("#table-div-keyword").val();
        var accountStr = $("#table-div-accountStr").val();

        var reg = /^[0-9]*$/;
        if(name==""||keyword==""){
            alert("有必填项未填");
            return false;
        }
        if(tel!="" && !reg.test(tel)){
            alert("联系方式有非法字符！");
            return false;
        }

        $.ajax({
            type: "post",
            async: true,
            url: "/core/modify_answerer",
            data: {
                "id": id,
                "name": name,
                "department":department,
                "tel": tel,
                "keyword":keyword,
                "accountStr":accountStr,
                "mediaId":mediaId
            },
            success: function (result) {
                if (result == "success") {
                    department = department!=""?department:"/";
                    tel = tel!=""?tel:"/";
                    accountStr = accountStr!=""?accountStr:"已绑定";

                    $("#answerer-table").find("tr").eq(i).find("td").eq(1).text(name);
                    $("#answerer-table").find("tr").eq(i).find("td").eq(2).text(department);
                    $("#answerer-table").find("tr").eq(i).find("td").eq(3).text(tel);
                    keyword = "["+keyword.replace("，",",")+"]";
                    $("#answerer-table").find("tr").eq(i).find("td").eq(4).text(keyword);
                    $("#answerer-table").find("tr").eq(i).find("td").eq(5).text(accountStr);
                    $(".modify-answer-div").css("display","none");
                    setTimeout(function(){alert('修改成功')},500);
                } else {
                    alert(result);
                }
            },
            error: function () {
                alert("出故障了请稍候再试");
            }
        })
    }
    //取消修改回答者
    function cancelModify(){
        $(".modify-answer-div").css("display","none");
    }
    //保存增加回答者
    function saveAdd(){
        //输入检测
        var name = $("#add-table-div-name").val();
        var department = $("#add-table-div-department").val();
        var tel = $("#add-table-div-tel").val();
        var keyword = $("#add-table-div-keyword").val();
        var accountStr = $("#add-table-div-accountStr").val();
        var mediaId = $("#mediaId").val();

        var reg = /^[0-9]*$/;
        if(name==""||keyword==""||accountStr==""){
            alert("有必填项未填");
            return false;
        }
        if(tel!="" && !reg.test(tel)){
            alert("联系方式有非法字符！");
            return false;
        }

        $.ajax({
            type: "post",
            async: true,
            url: "/core/add_answerer",
            data: {
                "name": name,
                "department":department,
                "tel": tel,
                "keyword":keyword,
                "accountStr":accountStr,
                "mediaId":mediaId
            },
            success: function (result) {
                if (reg.test(result)) {
                    setTimeout(function(){
                        alert("添加成功")
                    },500);
                    window.location.reload();
                } else {
                    alert(result);
                }
            },
            error: function () {
                alert("出故障了请稍候再试");
            }
        })

    }
    //取消增加回答者
    function cancelAdd(){
        $(".add-answer-div").css("display","none");
    }

    //删除回答者
    function deleting(i,id){
        $.ajax({
            type: "post",
            async: true,
            url: "/core/delete_answerer",
            data: {
                "answererId": id
            },
            success: function (result) {
                if (result == "success") {
                    setTimeout(function(){alert('删除成功')},1000);
                    $("#answerer-table").find("tr").eq(i).remove();
                    flushList();
                } else {
                    alert(result);
                }
            },
            error: function () {
                alert("出故障了请稍候再试");
            }
        });
    }


    //保存修改知识库
    function saveQAModify(){
        var i =$(".modify-qa-div").attr("id");
        var id = $("#qa-table").find("tr").eq(i).find("td").eq(0).attr('id');
        var mediaId = $("#mediaId").val();
        //输入检测
        var question = $("#table-div-question").val();
        var answer = $("#table-div-answer").val();

        if(question==""||answer==""){
            alert("有必填项未填");
            return false;
        }
        $.ajax({
            type: "post",
            async: true,
            url: "/core/modify_qa",
            data: {
                "id": id,
                "question": question,
                "answer":answer,
                "mediaId":mediaId
            },
            success: function (result) {
                if (result == "success") {
                    $("#qa-table").find("tr").eq(i).find("td").eq(1).text(question);
                    $("#qa-table").find("tr").eq(i).find("td").eq(2).text(answer);
                    $(".modify-qa-div").css("display","none");
                    setTimeout(function(){alert('修改成功')},500);
                } else {
                    alert(result);
                }
            },
            error: function () {
                alert("出故障了请稍候再试");
            }
        })
    }
    //取消修改知识库
    function cancelQAModify(){
        $(".modify-qa-div").css("display","none");
    }
    //增加一条知识库
    function saveQAAdd(){
        //输入检测
        var question = $("#add-table-div-question").val();
        var answer = $("#add-table-div-answer").val();
        var mediaId = $("#mediaId").val();

        var reg = /^[0-9]*$/;

        if(question==""||answer==""){
            alert("有必填项未填");
            return false;
        }
        $.ajax({
            type: "post",
            async: true,
            url: "/core/add_qa",
            data: {
                "question": question,
                "answer":answer,
                "mediaId":mediaId
            },
            success: function (result) {
                if (reg.test(result)) {
                    setTimeout(function(){alert('添加成功')},500);
                    window.location.reload();
                } else {
                    alert(result);
                }
            },
            error: function () {
                alert("出故障了请稍候再试");
            }
        })

    }
    //取消增加
    function cancelQAAdd(){
        $(".add-qa-div").css("display","none");
    }

    //删除知识库
    function deleteQA(i,id){
        $.ajax({
            type: "post",
            async: true,
            url: "/core/delete_qa",
            data: {
                "id": id
            },
            success: function (result) {
                if (result == "success") {
                    setTimeout(function(){alert('删除成功')},1000);
                    $("#qa-table").find("tr").eq(i).remove();
                    flushQAList();
                } else {
                    alert(result);
                }
            },
            error: function () {
                alert("出故障了请稍候再试");
            }
        });
    }


    //刷新序号
    function flushList() {
        var rows = $("#answerer-table").find("tr").length;
        for(var i=1;i<rows;i++){
            $("#answerer-table").find("tr").eq(i).find("td").eq(0).text(i);
        }
    }

    //刷新序号
    function flushQAList() {
        var rows = $("#qa-table").find("tr").length;
        for(var i=1;i<rows;i++){
            $("#qa-table").find("tr").eq(i).find("td").eq(0).text(i);
        }
    }

    //上传回答者文件
    function upload_answerer(){
        var media_id = $("#mediaId").val();
        $('#form_answerer').ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: 'core/file_upload?type=answerer&media_id='+media_id, // 需要提交的 url
            success: function (result) { // data 保存提交后返回的数据，一般为 json 数据
                alert(result);
            },
            error: function () {
                alert("上传失败");
            }
        });
    }

    //上传知识库文件
    function upload_qa(){
        var media_id = $("#mediaId").val();
        $('#form_qa').ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: 'core/file_upload?type=qa&media_id='+media_id, // 需要提交的 url
            success: function (result) { // data 保存提交后返回的数据，一般为 json 数据
                alert(result);
            },
            error: function () {
                alert("上传失败");
            }
        });
    }

