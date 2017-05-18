$(function(){

	//根据总页数判断，如果小于5页，则显示所有页数，如果大于5页，则显示5页。根据当前点击的页数生成

	//每页显示的数目
	var show_per_page_num = 6;

	//获取content对象里面，数据的数量
	var num_of_items = $("#qa-table").find("tr").length-1;

	//计算页面显示的总页数
	var pageCounter = Math.ceil(num_of_items/show_per_page_num);

	//隐藏该对象下面的所有子元素
	$('#qa-table').find("tr").css('display', 'none');

	//显示第n（show_per_page）元素
	$('#qa-table').find("tr").slice(0, show_per_page_num+1).css('display', 'table-row');

	//隐藏域默认值
	$('#qa_start_page').val(0);
	$('#qa_current_page').val(0);
	$('#qa_show_per_page').val(show_per_page_num);
	$('#qa_end_page').val(pageCounter);

	//生成分页按钮
	if(pageCounter>5){
		page_icon_num(1,5,0);
	}else{
		page_icon_num(1,pageCounter,0);
	}

	//点击分页按钮触发
	$("body").on("click","#pageQA li",function(){
		var pageNum = parseInt($(this).html())-1;//获取当前页数

		var page = pageNum +1;//跳转页码数
		var show_per_page = parseInt($('#qa_show_per_page').val());

		//开始
		start_point = pageNum * show_per_page+1;
		//结束
		end_point = start_point + show_per_page;
		//隐藏内容ul的所有子元素，获取特定项目并显示它们
		$('#qa-table').find("tr").css('display', 'none').slice(start_point, end_point).css('display', 'table-row');
		$('#qa-table').find("tr").eq(0).css('display', 'table-row');

		if(pageCounter > 5){
			//显示页面
			pageGroup(page,pageCounter);
		}else{
			$(this).addClass("on");
			$(this).siblings("li").removeClass("on");
		}
	});

	//点击上一页触发
	$("#pageQA .QAPageUp").click(function(){
		var pageNum = parseInt($("#pageQA li.on").html());//获取当前页
		if (pageNum <= 1) {
			var page = pageNum;
		}else{
			var page = pageNum-1;
		}
		var show_per_page = parseInt($('#qa_show_per_page').val());

		//开始
		start_point = page * show_per_page - show_per_page+1;
		//结束
		end_point = start_point + show_per_page;

		//隐藏内容ul的所有子元素，获取特定项目并显示它们
		$('#qa-table').find("tr").css('display', 'none').slice(start_point,end_point).css('display', 'table-row');
        $('#qa-table').find("tr").eq(0).css('display', 'table-row');

		if(pageCounter > 5){
			pageUpA(pageNum,pageCounter);
		}else{
			var index = $("#pageQA ul li.on").index();//获取当前页
			if(index > 0){
				$("#pageQA li").removeClass("on");//清除所有选中
				$("#pageQA ul li").eq(index-1).addClass("on");//选中上一页
			}
		}
	});
	
	//点击下一页触发
	$("#pageQA .QAPageDown").click(function(){
		var pageNum = parseInt($("#pageQA li.on").html());//获取当前页
		var page = pageNum;
		if (pageNum===pageCounter) {
			page = pageNum-1;
		}
		var show_per_page = parseInt($('#qa_show_per_page').val());
		//开始
		start_point = page * show_per_page+1;

		//结束
		end_point = start_point + show_per_page;

		//隐藏内容ul的所有子元素，获取特定项目并显示它们
		$('#qa-table').find("tr").css('display', 'none').slice(start_point, end_point).css('display', 'table-row');
        $('#qa-table').find("tr").eq(0).css('display', 'table-row');
		if(pageCounter > 5){
			pageDownA(pageNum,pageCounter);
		}else{
			var index = $("#pageQA ul li.on").index();//获取当前页
			if(index+1 < pageCounter){
				$("#pageQA li").removeClass("on");//清除所有选中
				$("#pageQA ul li").eq(index+1).addClass("on");//选中上一页
			}
		}
	});

	//点击首页
	$("body").on("click","#pageQA .QAPagestart",function(){
		var pageNum = $('#qa_start_page').val();
		//开始
		start_point = pageNum * show_per_page_num+1;
		//结束
		end_point = start_point + show_per_page_num;

		//隐藏内容ul的所有子元素，获取特定项目并显示它们
		$('#qa-table').find("tr").css('display', 'none').slice(start_point, end_point).css('display', 'table-row');
        $('#qa-table').find("tr").eq(0).css('display', 'table-row');
		if (pageCounter > 5) {
			//显示页码
			pageGroup(1,pageCounter);
		}else{
			var index = $("#pageQA ul li.on").index();//获取当前页
			if(index < pageCounter){
				$("#pageQA li").removeClass("on");//清除所有选中
				$("#pageQA ul li:first").addClass("on");
			}
		}
	});

	//点击尾页
	$("body").on("click","#pageQA .QAPageend",function(){
		var pageNum1 = $('#qa_end_page').val();
		var pagenum = pageNum1-2
		var page = pageNum1-1;

		//开始
		start_point = page * show_per_page_num+1;
		//结束
		end_point = start_point + show_per_page_num;

		//隐藏内容ul的所有子元素，获取特定项目并显示它们
		$('#qa-table').find("tr").css('display', 'none').slice(start_point, end_point).css('display', 'table-row');
        $('#qa-table').find("tr").eq(0).css('display', 'table-row');

		if (pageCounter > 5) {
			//显示页码
			pageGroup(pagenum,pageNum1);
			$("#pageQA ul li:last-child").addClass("on").siblings().removeClass("on");
		}else{
			var index = $("#pageGro ul li.on").index();//获取当前页
			if(index < pageCounter){
				$("#pageQA li").removeClass("on");//清除所有选中
				$("#pageQA ul li:last-child").addClass("on");
			}
		}

	});
});

//点击跳转页面
function pageGroup(pageNum,pageCount){
	switch(pageNum){
		case 1:
			page_icon_num(1,5,0);
		break;
		case 2:
			page_icon_num(1,5,1);
		break;
		case pageCount-1:
			page_icon_num(pageCount-4,pageCount,3);
		break;
		case pageCount:
			page_icon_num(pageCount-4,pageCount,4);
		break;
		default:
			page_icon_num(pageNum-2,pageNum+2,2);
		break;
	}
}

//根据当前选中页生成页面点击按钮
function page_icon_num(page, count, eq){
	var ul_html = "";
	for(var i=page; i<=count; i++){
		ul_html += "<li>"+i+"</li>";
	}
	$("#pageQA ul").html(ul_html);
	$("#pageQA ul li").eq(eq).addClass("on");
}

//上一页
function pageUpA(pageNum, pageCount){
	switch(pageNum){
		case 1:
		break;
		case 2:
			page_icon_num(1,5,0);
		break;
		case pageCount-1:
			page_icon_num(pageCount-4,pageCount,2);
		break;
		case pageCount:
			page_icon_num(pageCount-4,pageCount,3);
		break;
		default:
			page_icon_num(pageNum-2,pageNum+2,1);
		break;
	}
}

//下一页
function pageDownA(pageNum, pageCount){
	switch(pageNum){
		case 1:
			page_icon_num(1,5,1);
		break;
		case 2:
			page_icon_num(1,5,2);
		break;
		case pageCount-1:
			page_icon_num(pageCount-4,pageCount,4);
		break;
		case pageCount:
		break;
		default:
			page_icon_num(pageNum-2,pageNum+2,3);
		break;
	}
}