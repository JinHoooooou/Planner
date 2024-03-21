$(document).ready(function () {
    $("#findPw").click(function(event) {
        event.preventDefault();
        let formData = $(this).serialize();

        $.ajax({
            url: "/user/findpw",
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            dataType:"html",
            data: formData,
            success: function (data) {
                var $div = $('<div></div>');
                var text = document.createTextNode(data);
                $div.append(data);
                $div.appendTo($('#youpw'))
            },
            error: function (xhr, textStatus, errorThrown) {
                alert("존재하지 않는 아이디 입니다.");
            }, 
        })
    });
})