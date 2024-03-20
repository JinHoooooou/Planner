$("#passwordResetForm").submit(function(event) {
    event.preventDefault();
    if (true) { // 만약에 아이디가 존재하는지 + 입력된 값을 올바르게 적었는지
        if (confirm("당신의 비밀번호는 pass1입니다. 로그인하시겠습니까?")) {
        location.href = './signin.html';
        } else {}
    } else {
        alert("입력된 값이 올바르지 않습니다.");
    }
});