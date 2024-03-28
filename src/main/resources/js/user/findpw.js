$(window).ready(function () {
  $("#youpw").click(function () {
    let userId = $('#userId').val();

    $.ajax({
      url: "/user/find/password",
      type: "POST",
      dataType: "json",
      data: { "userId": userId },
      success: function (response) {
        alert(response.message)
        document.location.href = "./signin.html"
      },
      error: function (xhr) {
        alert(xhr.responseJSON.message);
      },
    })
  });

  $.ajax({
    url: "/user",
    type: "GET",
    dataType: "json",
    success: function () {
      alert("로그아웃 먼저 하세요.")
      window.location.href = '/main.html';
    },
    error: function () { }
  })
})
