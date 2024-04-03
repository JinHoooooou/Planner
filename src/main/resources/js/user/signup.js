const userIdRegex = /^[A-Za-z][A-Za-z0-9_]{7,16}$/
const nicknameRegex = /^[가-힣a-zA-Z0-9]{3,20}$/
const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()-_=+]).{8,20}$/

const userIdMessage = "영문자로 시작해야 하며 8~16자의 영문자, 숫자, _를 사용해야합니다.";
const userPwMessage = "8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해야합니다.";
const userPwConfirmMessage = "비밀번호와 비밀번호 확인이 일치하지 않습니다";
const nicknameMessage = "3~20자의 한글, 영문, 숫자를 사용해야합니다.";

$(document).ready(function () {
  $("#userIdDuplicateButton").on("click", requestDuplicateUserId)
  $("#nicknameDuplicateButton").on("click", requestDuplicateNickname)
  $("#signUpForm").on("submit", requestSignUp)
  $('#userId').on("blur", function () {
    $('#userIdErrorMessage').text(!userIdRegex.test($(this).val()) ? userIdMessage : "");
  });
  $("#userPw").on("blur", function () {
    $('#userPwErrorMessage').text(!passwordRegex.test($(this).val()) ? userPwMessage : "");
  })
  $('#userPwConfirm').on("blur", function () {
    $('#userPwConfirmErrorMessage').text($(this).val() !== $("#userPw").val() ? userPwConfirmMessage : "");
  })
  $('#nickname').on("blur", function () {
    $("#nicknameErrorMessage").text(!nicknameRegex.test($(this).val()) ? nicknameMessage : "")
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

function requestDuplicateUserId() {
  let userId = $("#userId").val();
  $.ajax({
    url: "/api/user/duplicate/userid",
    type: "GET",
    data: {"userId": userId},
    dataType: "json",
    success: function (response) {
      alert(response.message);
    },
    error: function (xhr) {
      alert(xhr.responseJSON.message);
    }
  })
}

function requestDuplicateNickname() {
  let nickname = $("#nickname").val();
  console.log(nickname);
  if (!nicknameRegex.test(nickname)) {
    alert("닉네임은 3~20자의 한글, 영문, 숫자를 사용해야합니다.")
    return;
  }
  $.ajax({
    url: `/user/duplicate/nickname/${nickname}`,
    type: "GET",
    dataType: "json",
    success: function () {
      alert("사용 가능한 닉네임 입니다.")
    },
    error: function (xhr) {
      console.log("ㅋㅋ");
      alert(xhr.responseJSON.message);
    }
  })
}

function requestSignUp() {
  event.preventDefault();
  let signUpForm = $(this).serialize();
  $.ajax({
    url: "/user/signup",
    type: "POST",
    contentType: "application/x-www-form-urlencoded",
    data: signUpForm,
    dataType: "json",
    success: function () {
      alert("회원가입이 완료되었습니다.");
      document.location.href = '/user/signin.html';
    },
    error: function (xhr) {
      alert(xhr.responseJSON.message)
    }
  })
}
