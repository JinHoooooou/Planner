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
})
