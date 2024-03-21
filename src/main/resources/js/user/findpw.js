$("#youpw").click(function(event) {
    let userId = $('#userId').val();

    $.ajax({
        url: `/user/findpw?userId=${userId}`,
        type: "GET",
        dataType:"html",
        data:{
            userId : $('#userId').val()
        },
        success: function (data) {
            if(confirm(userId+"님의 비밀번호는"+data+"입니다. 로그인하시겠습니까?")) {
                document.location.href = "./signin.html"
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            alert("존재하지 않는 아이디 입니다.");
        }, 
    })
});
