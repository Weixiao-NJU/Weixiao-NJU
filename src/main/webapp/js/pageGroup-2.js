$(function(){
    $("#loading").hide();
	//根据总页数判断，如果小于5页，则显示所有页数，如果大于5页，则显示5页。根据当前点击的页数生成

	//每页显示的数目
	var show_lecture_per_page = 10;

	//获取页面显示的总页数
	var pageCountNum = parseInt($("#lecture_num_page").val());

	//隐藏域默认值
	$('#lecture_start_page').val(1);
	$('#lecture_current_page').val(1);
	$('#show_lecture_per_page').val(show_lecture_per_page);
	$('#lecture_end_page').val(pageCountNum);

	//生成分页按钮
	if(pageCountNum>5){
		page_lecture_icon(1,5,0);
	}else{
        page_lecture_icon(1,pageCountNum,0);
	}

	//点击分页按钮触发
	$("body").on("click","#pageLecture li",function(){
		var page = parseInt($(this).html());//跳转页码数
		var show_lecture_per_page = parseInt($('#show_lecture_per_page').val());

		if(pageCountNum > 5){
			//显示页面
			pageLectureGroup(page,pageCountNum);
		}else{
			$(this).addClass("on");
			$(this).siblings("li").removeClass("on");
		}
        //获取特定项目并显示它们
        getLectures(page);
	});

	//点击上一页触发
	$("#pageLecture .LPageUp").click(function(){
		var pageNum = parseInt($("#pageLecture li.on").html());//获取当前页
		if (pageNum <= 1) {
			return;
		}else{
			var page = pageNum-1;
		}
		var show_lecture_per_page = parseInt($('#show_lecture_per_page').val());

		if(pageCountNum > 5){
			LpageUp(pageNum,pageCountNum);
		}else{
			var index = $("#pageLecture ul li.on").index();//获取当前页
			if(index > 0){
				$("#pageLecture li").removeClass("on");//清除所有选中
				$("#pageLecture ul li").eq(index-1).addClass("on");//选中上一页
			}
		}
        //获取特定项目并显示它们
        getLectures(page);
	});
	
	//点击下一页触发
	$("#pageLecture .LPageDown").click(function(){
		var pageNum = parseInt($("#pageLecture li.on").html());//获取当前页
        var page = pageNum+1;
		if (pageNum===pageCountNum) {
			return;
		}
		if(pageCountNum > 5){
			LpageDown(pageNum,pageCountNum);
		}else{
			var index = $("#pageLecture ul li.on").index();//获取当前页
			if(index+1 < pageCountNum){
				$("#pageLecture li").removeClass("on");//清除所有选中
				$("#pageLecture ul li").eq(index+1).addClass("on");//选中上一页
			}
		}
        //获取特定项目并显示它们
        getLectures(page);
	});

	//点击首页
	$("#tab3").on("click","#pageLecture .LPagestart",function(){
		var pageNum = $('#lecture_start_page').val();

		if (pageCountNum > 5) {
			//显示页码
			pageLectureGroup(1,pageCountNum);
		}else{
			var index = $("#pageLecture ul li.on").index();//获取当前页
			if(index < pageCountNum){
				$("#pageLecture li").removeClass("on");//清除所有选中
				$("#pageLecture ul li:first").addClass("on");
			}
		}
        //获取特定项目并显示它们
        getLectures(pageNum);
	});

	//点击尾页
	$("#tab3").on("click","#pageLecture .LPageend",function(){
		var pageNum1 = $('#lecture_end_page').val();
		var pagenum = pageNum1-2;

		if (pageCountNum > 5) {
			//显示页码
			pageLectureGroup(pagenum,pageNum1);
			$("#pageLecture ul li:last-child").addClass("on").siblings().removeClass("on");
		}else{
			var index = $("#pageLecture ul li.on").index();//获取当前页
			if(index < pageCountNum){
				$("#pageLecture li").removeClass("on");//清除所有选中
				$("#pageLecture ul li:last-child").addClass("on");
			}
		}
		//获取特定项目并显示它们
        getLectures(pageNum1);
	});
});

//点击跳转页面
function pageLectureGroup(pageNum,pageCountNum){
	switch(pageNum){
		case 1:
            page_lecture_icon(1,5,0);
		break;
		case 2:
            page_lecture_icon(1,5,1);
		break;
		case pageCountNum-1:
            page_lecture_icon(pageCountNum-4,pageCountNum,3);
		break;
		case pageCountNum-0:
            page_lecture_icon(pageCountNum-4,pageCountNum,4);
		break;
		default:
            page_lecture_icon(pageNum-2,pageNum+2,2);
		break;
	}
}

//根据当前选中页生成页面点击按钮
function page_lecture_icon(page,count,eq){
	var ul_html = "";
	for(var i=page; i<=count; i++){
		ul_html += "<li>"+i+"</li>";
	}
	$("#pageLecture ul").html(ul_html);
	$("#pageLecture ul li").eq(eq).addClass("on");
}

//上一页
function LpageUp(pageNum,pageCountNum){
	switch(pageNum){
		case 1:
		break;
		case 2:
            page_lecture_icon(1,5,0);
		break;
		case pageCountNum-1:
            page_lecture_icon(pageCountNum-4,pageCountNum,2);
		break;
		case pageCountNum:
            page_lecture_icon(pageCountNum-4,pageCountNum,3);
		break;
		default:
            page_lecture_icon(pageNum-2,pageNum+2,1);
		break;
	}
}

//下一页
function LpageDown(pageNum,pageCountNum){
	switch(pageNum){
		case 1:
            page_lecture_icon(1,5,1);
		break;
		case 2:
            page_lecture_icon(1,5,2);
		break;
		case pageCountNum-1:
            page_lecture_icon(pageCountNum-4,pageCountNum,4);
		break;
		case pageCountNum:
		break;
		default:
            page_lecture_icon(pageNum-2,pageNum+2,3);
		break;
	}
}

function getLectures(page){
    $('#lecture-table').find("tr").slice(1,11).remove();
    $("#loading").show();
    $.ajax({
        type: "post",
        async: true,
        url: "/core/get_lecture_list",
        data: {
            "pageNum": page
        },
        success: function (result) {
            var point = (page-1) * 10 + 1;
        	$.each(result,function(i, item) {
                var start = new Date(parseInt(item.startTime)).format("yyyy-MM-dd hh:mm");
                var end = new Date(parseInt(item.endTime)).format("hh:mm");
                var trHTML = "<tr>"+
                    "<td style=\"text-align:center;\">"+point+"</td>"+
                    "<td>"+item.title+"</td>"+
                    "<td style=\"text-align:center;\">"+item.teacher+"</td>"+
                    "<td style=\"text-align:center;\">"+item.academy+"</td>"+
                    "<td>"+item.introduction+"</td>"+
                    "<td>"+start+" - "+end+"</td>"+
                    "<td style=\"text-align:center;\">"+item.place+"</td>"+
                    "<td style=\"text-align:center;\">"+item.subscriberNum+"</td>"+
                    "<td style=\"text-align:center;\">"+item.interestNum+"</td>"+
                    "</tr>";
                $('#lecture-table').append(trHTML);//在table最后面添加一行
				point = point+1;
                $("#loading").hide();
            });
        },
        error: function () {
            alert("出故障了请稍候再试");
        }
    });
}

Date.prototype.format = function(format) {
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(),    //day
        "h+" : this.getHours(),   //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
        (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length==1 ? o[k] :
                ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}