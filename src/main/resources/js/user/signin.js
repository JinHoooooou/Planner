$(window).ready(function () {
  $("#loginForm").submit(function (event) {
    event.preventDefault();
    let formData = $(this).serialize();
    $.ajax({
      url: "/api/user/signin",
      type: "POST",
      contentType: "application/x-www-form-urlencoded",
      data: formData,
      dataType: "json",
      success: function () {
        window.location.href = "/main.html";
      },
      error: function (xhr) {
        alert(xhr.responseJSON.message);
      }
    })
  })
  $.ajax({
    url: "/user",
    type: "GET",
    dataType: "json",
    success: function () {
      alert("로그아웃 먼저 하세요.")
      window.location.href = '/main.html';
    },
    error: function () {
    }
  })
})
