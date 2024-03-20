$(document).ready(function () {
    $("#findPw").submit(function(event) {
        event.preventDefault();
        let formData = $(this).serialize();

        $.ajax({
            url: "/user/findpw",
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            data: formData,
            success: function (response, textStatus, xhr) {
                alert("비밀번호는"+result+"입니다.")
                document.location.href = './signin.html';
            },
            error: function (xhr, textStatus, errorThrown) {
                alert("존재하지 않는 아이디 입니다.");
            }, 
        })
    });
})