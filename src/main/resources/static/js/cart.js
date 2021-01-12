$(function () {


    $(".edit").click(function () {
        $("#inputCount").val($(this).attr("data"));
        $("#updCartCount").attr("data-id",$(this).attr("data-id"));

    });


    $("#updCartCount").click(function () {

        if ($("#inputCount").val()<=0 || $("#inputCount").val()>999){
            alert("数量不合法");
            return ;
        }
        var pattern = $(this).attr("data-id") + "/" + $("#inputCount").val();
        console.log(pattern);
        $("form").attr("action","/cart/updCount/" + pattern);
        $("form").submit();
    });


    $(".del").click(function () {
        $("form").attr("action","/cart/delItem/" + $(this).attr("data-id"));
        $("form").submit();
    });

    var totalRow = 0;
    var total = 0; //商品总数
    $('.cart tr').each(function() {
        $(this).find('td:eq(4)').each(function(){
            console.log($(this));
            totalRow += parseFloat($(this).text());
            total++;
        });
    });

    $('.money').text(totalRow);


    $(".small-image").bind("mouseover mouseout mousemove", function (event) {
        var $img = $(this);

        if (event.type == "mouseover"){
            $(this).find(".desc").show();
            var $div=$("<div id='tooltip' style='position: absolute'><img src='"+ $img.attr("src") +"' width='225px' height='270px'></img></div>");
            $("body").append($div);//把div添加到body中
            $("#tooltip").css({
                top:event.pageY+10+"px",
                left:event.pageX+12+"px"
            }).show();
        }else if (event.type == "mouseout"){
            $(this).find(".desc").hide();

            $("#tooltip").remove();
        }else if (event.type == "mousemove"){

            $("#tooltip").css({
                top:event.pageY+10+"px",
                left:event.pageX+12+"px"
            }).show();
        }
    });


    $(".closeConfirm").click(function () {
        $("#confirm").hide();

    });
    $(".buy").click(function () {
        if (total==0){
            alert("当前购物车为空！不能结算！");
            return;
        }
        $("#confirm").show();
        console.log($('.cart tr:eq(1)'));
        if (total>1){
            $("#message").text("确认要购买《" + $('.cart tr:eq(1)').find('td:eq(1)').text() +"》等" + total + "件商品吗？");
        }else{
            $("#message").text("确认要购买《" + $('.cart tr:eq(1)').find('td:eq(1)').text() +"》吗？");
        }
    });
    $("#cr-order").click(function () {
        $("#confirm").hide();
        $("form").attr("action","/cart/createOrder");
        $("form").submit();
        return;
    });
});

window.onload = function () {
    if (window.name == "hasLoad") {
        location.reload();
        window.name = "";
    } else {
        window.name = "hasLoad";
    }


};

