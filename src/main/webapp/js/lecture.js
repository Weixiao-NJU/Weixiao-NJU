function interest(){
    var lid = parseInt($("#lid").val());
    $.ajax({
        type: "post",
        async: true,
        url: "/core/interest",
        data: {
            "lid":lid
        },
        success: function (result) {
            window.location.reload();
        },
        error: function () {
            alert("出故障了请稍候再试");
        }
    });
}