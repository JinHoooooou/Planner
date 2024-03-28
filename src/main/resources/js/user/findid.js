$(window).ready(function () {
  $("#youid").click(function () {
    let userName = $('#userName').val();
    let phone = $('#phone').val();

    $.ajax({
      url: "/user/find/id",
      type: "POST",
      dataType: "json",
      data: {
        "userName": userName,
        "phone": phone
      },
      success: function (response) {
        alert(response.message);
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

