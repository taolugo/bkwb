$(function () {
    $(".showDetail").click(function () {
        $("#tbd").empty();
        var data = $(this).attr("data");
        var arr = $.parseJSON(data);
        for (var i = 0; i < arr.length; i++) {
            console.log(arr[i]);
            var tr = "<tr>\n" +
                "                        <th scope=\"row\">" + arr[i].bookId + "</th>\n" +
                "                        <td><a href='/book/" + arr[i].bookId + "'>" + arr[i].bookName + "</a></td>\n" +
                "                        <td>" + arr[i].bookPrice + "</td>\n" +
                "                        <td>" + arr[i].count + "</td>\n" +
                "                    </tr>";
            $("#tbd").append(tr);
        }
        $('#staticBackdrop').modal('show');

    });


});


