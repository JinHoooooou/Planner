$(window).ready(function () {
    $("#signUpForm").submit(function (event) {
        event.preventDefault();
        let formData = $(this).serialize();
        console.log(formData)
        $.ajax({
            url: "/user/signup",
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            data: formData,
            dataType: "json",
            success: function () {
                alert("회원가입이 완료되었습니다.");
                document.location.href = '../user/login.html';
            },
            error: function (xhr, status, error) {
                console.log(xhr);
                console.log(status.status);
                console.log(xhr.responseText)
                console.log(xhr.responseJSON)

                alert(xhr.responseText)
            }
        })
    })

})

function checkNicknameDuplicate() {
    let nickname = $("#nickname").val();
    if (nickname === "") {
        alert("닉네임을 입력해주세요")
        return;
    }
    $.ajax({
        url: `/user/signup/nickname/duplicate?nickname=${nickname}`,
        type: "GET",
        success: function () {
            alert("사용 가능한 닉네임 입니다.")
        },
        error: function (xhr) {
            if (xhr.status === 400) {
                alert("잘못된 요청입니다.")
            } else if (xhr.status === 403) {
                alert("이미 존재하는 닉네임 입니다.")
            }
        }
    })
}

function checkUserIdDuplicate() {
    let userId = $("#userId").val();
    if (userId === "") {
        alert("아이디를 입력해주세요");
        return;
    }
    $.ajax({
        url: `/user/signup/userid/duplicate?userId=${userId}`,
        type: "GET",
        success: function () {
            alert("사용 가능한 아이디 입니다.")
        },
        error: function (xhr) {
            if (xhr.status === 400) {
                alert("잘못된 요청입니다.")
            } else if (xhr.status === 403) {
                alert("이미 존재하는 아이디 입니다.")
            }
        }
    })
}