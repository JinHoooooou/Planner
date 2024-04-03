$(window).ready(function () {
  $.ajax({
    url: "/api/user/info",
    type: "GET",
    dataType: "json",
    success: renderInfo,
    error: function (xhr) {
      alert(xhr.responseJSON.message);
      if (xhr.status === 401) {
        window.location.href = "/index.html";
      } else {
        window.location.href = "/main.html";
      }
    }
  })

  $("#updateSubmitButton").on("click", requestUpdateUser);
  $("#deleteUserButton").on("click", requestDeleteUser);
})

function renderInfo(response) {
  let userInfo = response.data
  $("#userId").val(userInfo.userId);
  $("#userPw").val(userInfo.userPw);
  $("#userPwConfirm").val(userInfo.userPw);
  $("#userName").val(userInfo.userName);
  $("#nickname").val(userInfo.nickname);
  $("#email").val(userInfo.email);
  $("#phone").val(userInfo.phone);
}

function requestUpdateUser() {
  $.ajax({
    url: "/api/user/update",
    type: "POST",
    data: {
      "userPw": $("#userPw").val(),
      "userPwConfirm": $("#userPwConfirm").val(),
      "email": $("#email").val(),
      "phone": $("#phone").val(),
    },
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
      url: "/api/user/delete",
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
