$('form').submit(function(event) {
    event.preventDefault();
    if (true) { // 사용자가 입력한 값이 맞다면
        if (confirm('당신의 아이디는 user1입니다. 로그인하시겠습니까?')) {
            location.href = './signin.html';
        } else {}
    } else {
    alert('입력된 값이 올바르지 않거나 존재하지 않는 아이디입니다.');
    }
});