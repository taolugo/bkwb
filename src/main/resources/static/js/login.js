$(function () {
    $(".login_button").click(function () {
        var username = $("#username").val();
        var password = $("#password").val();
        var captcha = $("#captcha").val();

        if (username == ""){
            alert("用户名不能为空");
            return false;
        }

        if (password == ""){
            alert("密码不能为空");
            return false;
        }
        if (captcha == ""){
            alert("验证码不能为空");
            return false;
        }

        $("form").submit();
    });
});