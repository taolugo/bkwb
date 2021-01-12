$(function () {
    $(".buy").click(function () {
        if (confirm("确定要购买《" + $("#title").html() + "》吗")){
            alert("购买成功");
        }
    });


    $("#addToCart").click(function () {
        getData2("/getLoginUser","GET",userHandler,"text");

    });

    function userHandler(data) {
        if (data == undefined || data==""){
            alert("清先登录");
            location.href="/cart/addToCart/";
            return;
        }
        var count = $("#inputCount").val();
        if (count <= 0 || count > 999){
            alert("数量不合法");
            return;
        }
        var path = "/cart/addToCart/" + $("#addToCart").attr("data")
            + "/" + count;
        console.log("count====" + count);
        console.log("path====" + path);
        getData2(path,"GET",msgHandler,"text");
    }

    function msgHandler(data) {
        $("#inputCount").val(1);
        alert(data);
    }
});

