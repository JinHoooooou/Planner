$(document).ready(function () {
    $("#loginForm").submit(function (event) {
        event.preventDefault();
        let formData = $(this).serialize();

        $.ajax({
            url: "/user/login",
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            dataType:"html",
            data: formData,
            success: function (data) {
                var $div = $('<div></div>');
                var text = document.createTextNode(data);
                $div.append(data);
                $div.appendTo($('#youpw'));
            },
            error: function (xhr, textStatus, errorThrown) {
                alert("아이디 또는 비밀번호를 잘못 입력했습니다.");
            },
        });
    });
});