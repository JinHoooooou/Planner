$(document).ready(function () {
    $("#loginForm").submit(function (event) {
        event.preventDefault();
        let formData = $(this).serialize();

        $.ajax({
            url: "/user/login",
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            data: formData,
            success: function (response, textStatus, xhr) {
                document.location.href="../plan3.html";
            },
            error: function (xhr, textStatus, errorThrown) {
                alert("아이디 또는 비밀번호를 잘못 입력했습니다.");
            },
        });
    });
});