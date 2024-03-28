$(window).ready(function () {
  $.ajax({
    url: "/user",
    type: "GET",
    dataType: "json",
    success: renderInfo,
    error: function (xhr) {
      alert(xhr.responseJSON.message);
      window.location.href = "/index.html";
    }
  })

  $("#updateSubmitButton").on("click", requestUpdateUser);
  $("#deleteUserButton").on("click", requestDeleteUser);
})

function renderInfo(response) {
  $("#userId").val(response.userId);
  $("#userPw").val(response.userPw);
  $("#userPwConfirm").val(response.userPw);
  $("#userName").val(response.userName);
  $("#nickname").val(response.nickname);
  $("#email").val(response.email);
  $("#phone").val(response.phone);
}

function requestUpdateUser() {
  $.ajax({
    url: "/user",
    type: "PUT",
    data: JSON.stringify({
      "userPw": $("#userPw").val(),
      "userPwConfirm": $("#userPwConfirm").val(),
      "email": $("#email").val(),
      "phone": $("#phone").val(),
    }),
    dataType: "json",
    success: function () {
      alert("수정 되었습니다. 다시 로그인 해 주세요.")
      window.location.href = "/index.html";
    },
    error: function (xhr) {
      alert(xhr.responseJSON.message);
    }
  })
}

function requestDeleteUser() {
  if (confirm("정말 탈퇴하시겠습니까?")) {
    $.ajax({
      url: "/user",
      type: "DELETE",
      dataType: "json",
      success: function () {
        window.location.href = "/index.html";
      },
      error: function (xhr) {
        alert(xhr.responseJSON.message);
      }
    })
  }
}
