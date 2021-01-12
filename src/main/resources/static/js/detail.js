$(function () {
    var data = $("tbody").attr("tdata");
    var arr = $.parseJSON(data);
    for (var i = 0; i < arr.length; i++) {
        console.log(arr[i]);
        var tr = "        <tr>\n" +
            "            <th scope=\"row\">" + arr[i].bookId + "</th>\n" +
            "            <td><a href='/book/" + arr[i].bookId + "'>" + arr[i].bookName + "</a></td>\n" +
            "            <td>" + arr[i].bookPrice + "</td>\n" +
            "            <td>" + arr[i].count + "</td>\n" +
            "            <td>" + (arr[i].count*arr[i].bookPrice) + "</td>\n" +
            "        </tr>";
        $("tbody").append(tr);
    }


    $("#pay").click(function () {
        getData2("/user/getBalance","GET",setBalance,"text");
    });
    function setBalance(data) {
        $("#balance").text("（余额："+data+"）");
        $('#staticBackdrop').modal('show');
    }

    $("#sub").click(function () {
        $("form").submit();
    });
});


