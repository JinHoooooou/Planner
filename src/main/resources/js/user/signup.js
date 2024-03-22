$('#idcheckDuplicateBtn').click(function(event) {
    let userId = $('#userId').val();
    const reg = /^[A-Za-z][A-Za-z0-9_]{7,16}$/;
    if (!reg.test($('#userId').val())){
        alert("유효한 아이디가 아닙니다.");
        $('#idarea').html("영문자로 시작해야 하며 8~16자의 영문자, 숫자, _를 사용해야합니다.")
    } else {
        $.ajax({
            url: `/user/signup/userid/duplicate?userId=${userId}`,
            type: "GET",
            success: function (response, textStatus, xhr){
                alert("사용 가능한 아이디입니다.");
                $('#idarea').html("")
            },
            error: function (xhr, textStatus, errorThrown) {
                if (xhr.status == 400) {
                    alert("유효한 아이디가 아닙니다.");
                } else if (xhr.status == 403) {
                    alert("이미 사용중인 아이디 입니다.");
                }
            }
        })
    }
});

$('#nicknamecheckDuplicateBtn').click(function(event) {
    let nickname = $('#nickname').val();
    const reg = /^[가-힣a-zA-Z0-9]{3,20}$/;
    if (!reg.test($('#nickname').val())){
        alert("유효한 닉네임이 아닙니다.")
        $('#nnarea').html("3~20자의 한글, 영문, 숫자를 사용해야합니다");
    } else {
        $.ajax({
            url:`/user/signup/nickname/duplicate?nickname=${nickname}`,
            type: "GET",
            success: function (response, textStatus, xhr){
                alert("사용 가능한 닉네임입니다.");
                $('#nnarea').html("");
            },
            error: function (xhr, textStatus, errorThrown) {
                if (xhr.status == 400) {
                    alert("유효한 닉네임이 아닙니다.")
                } else if (xhr.status == 403) {
                    alert("이미 사용중인 닉네임 입니다.")
                }
            }  
            })
    }
});
$(document).ready(function () {
$('form').submit(function(event) {
    event.preventDefault();
    const reg = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$/;
    const nreg = /^[가-힣a-zA-Z0-9]{3,20}$/;
    const ireg = /^[A-Za-z][A-Za-z0-9_]{7,16}$/;
    if (!reg.test($('#userPw').val()) && $('#userPw').val() != $('#userPwConfirm').val()) {
        alert("비밀번호가 올바르지 않습니다");
        $('#pwarea').html("영문, 숫자 조합 8~25자리 이상 입력하시오.");
    } else if (!reg.test($('#userPw').val())) {
        alert("비밀번호가 유효하지 않습니다");
        $('#pwarea').html("영문, 숫자 조합 8~25자리 이상 입력하시오.");
    } else if ($('#userPw').val() != $('#userPwConfirm').val()) {
        alert("비밀번호가 일치하지 않습니다"); 
        $('#pwarea').html("");
    } else {
        let formData = $(this).serialize();
        $.ajax({
            url: "/user/signup",
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            data: formData,
            success: function (response, textStatus, xhr) {
                alert("회원가입이 완료되었습니다"); 
                document.location.href='./signin.html';
            },
            error: function (xhr, textStatus, errorThrown) {
                alert("회원가입에 실패하셨습니다"); 
            },
        });
    }
    });
});
