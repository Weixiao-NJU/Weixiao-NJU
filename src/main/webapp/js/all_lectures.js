$(document).ready(function () {
    $("#pageNum").val(1);
});
function loadMore() {
    var pageNum = parseInt($("#pageNum").val())+1;
    $("#loadingToast").css("display","block");
    $.ajax({
        type: "post",
        async: true,
        url: "/core/get_lecture_list",
        data: {
            "pageNum": pageNum
        },
        success: function (result) {
            if(result == "")
                $("#msg-div").css("display","block");
            $.each(result,function (i,item) {
                var start = new Date(parseInt(item.startTime)).format("yyyy-MM-dd hh:mm");
                var end = new Date(parseInt(item.endTime)).format("hh:mm");

                var temp = "<a href=\"/core/lecture?id="+item.id+"\" class=\"weui-media-box weui-media-box_appmsg\">"+
                    "<div class=\"weui-media-box__hd\">"+
                    "<div class=\"lecture-img-div\">"+
                    "<img class=\"weui-media-box__thumb\" src=\"../image/lecture.svg\" >"+
                    "</div>"+
                    "</div>"+
                    "<div class=\"weui-media-box__bd\">"+
                    "<h4 class=\"weui-media-box__title\">"+item.title+"</h4>"+
                    "<p class=\"weui-media-box__desc\">主讲："+item.teacher+"--"+item.academy+"</p>"+
                    "<p class=\"weui-media-box__desc\">时间："+start+" - "+end+"</p>"+
                    "<p class=\"weui-media-box__desc\">地点："+item.place+"</p>"+
                    "</div>"+
                    "</a>";
                $(".weui-panel__bd").append(temp);
            });
            $("#pageNum").val(pageNum);
            $("#loadingToast").css("display","none");
        },
        error: function () {

            alert("出故障了请稍候再试");
        }
    });

}

function ok() {
    $("#msg-div").css("display","none");
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
};