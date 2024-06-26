package com.kh.constant;

public interface Message {

  String ERROR_USER_ID = "아이디는 영문자로 시작해야 하며 8~16자의 영문자, 숫자, _를 사용해야합니다.";
  String DUPLICATE_USER_ID = "이미 존재하는 아이디 입니다.";
  String SUCCESS_USER_ID = "사용 가능한 아이디 입니다.";
  String NOT_EXIST_USER_ID = "존재하지 않는 아이디 입니다.";

  String ERROR_USER_PASSWORD = "비밀번호는 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해야합니다.";
  String ERROR_USER_PASSWORD_CONFIRM = "비밀번호와 비밀번호 확인이 일치하지 않습니다.";
  String NOT_EQUAL_USER_PASSWORD = "비밀번호가 일치하지 않습니다.";

  String ERROR_USER_NICKNAME = "닉네임은 3~20자의 한글, 영문, 숫자를 사용해야합니다.";
  String DUPLICATE_USER_NICKNAME = "이미 존재하는 닉네임입니다.";
  String SUCCESS_USER_NICKNAME = "사용 가능한 닉네임입니다.";

  String ERROR_USER_NAME = "이름은 2~20자의 한글을 사용해야합니다.";
  String ERROR_USER_EMAIL = "유효하지 않은 이메일입니다.";
  String ERROR_USER_PHONE = "유효하지 않은 휴대전화번호입니다.";

  String SUCCESS_SIGNUP = "환영합니다.\n가입이 완료되었습니다\n\n로그인 후 이용해주세요.";
  String SUCCESS_UPDATE_USER = "회원 정보가 변경되었습니다.\n다시 로그인하세요.";

  String INVALID_REQUEST = "올바른 요청이 아닙니다.";
  String INVALID_SESSION = "세션이 유효하지 않습니다.\n다시 로그인하세요.";

  String EMPTY_PLAN_TITLE = "제목을 입력해 주세요.";
  String EMPTY_PLAN_DATE = "시작/마감 날짜를 입력해 주세요.";
  String INVALID_PLAN_DATE = "시작 날짜는 마감 날짜보다 앞서야 합니다.";
  String INVALID_PLAN_ALARM_DATE = "알람 날짜는 마감 날짜보다 앞서야 합니다.";

  String EMPTY_DETAIL_DATE = "날짜를 입력해 주세요.";
  String EMPTY_DETAIL_TIME = "시작/종료 시간을 입력해 주세요.";
  String INVALID_DETAIL_DATE = "시작 날짜는 플랜의 시작~마감 사이어야 합니다.";
  String INVALID_DETAIL_TIME = "시작 시간은 종료 시간보다 앞서야 합니다.";
}
