$(window).ready(function () {
    $("#loginForm").submit(function (event) {
        event.preventDefault();
        let formData = $(this).serialize();
        $.ajax({
            url: "/user/signin",
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            data: formData,
            success: function () {
                window.location.href = "/main.html";
            },
            error: function () {
                alert("아이디 또는 비밀번호를 잘못 입력했습니다.");
            }
        })
    })
})
