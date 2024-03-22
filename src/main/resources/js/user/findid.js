$("#youid").click(function(event) {
    let userName = $('#userName').val();
    let nickname = $('#nickname').val();

    $.ajax({
        url: `/user/findid?userName=${userName}&nickname=${nickname}`,
        type: "GET",
        dataType:"html",
        data:{
            userName : $('#userName').val(),
            nickname : $('#nickname').val()
        },
        success: function (data) {
            if(confirm(userName+"("+nickname+")"+"님의 아이디는"+data+"입니다. 로그인하시겠습니까?")) {
                document.location.href = "./signin.html"
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            alert("이름 또는 아이디를 잘못 입력했거나 존재하지 않는 아이디 입니다.");
        }, 
    })
});
