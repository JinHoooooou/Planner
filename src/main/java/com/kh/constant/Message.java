package com.kh.constant;

public interface Message {

  String ERROR_USER_ID = "아이디는 영문자로 시작해야 하며 8~16자의 영문자, 숫자, _를 사용해야합니다.";
  String DUPLICATE_USER_ID = "이미 존재하는 아이디 입니다.";
  String SUCCESS_USER_ID = "사용 가능한 아이디 입니다.";

  String ERROR_USER_NICKNAME = "닉네임은 3~20자의 한글, 영문, 숫자를 사용해야합니다.";
  String DUPLICATE_USER_NICKNAME = "이미 존재하는 닉네임입니다.";
  String SUCCESS_USER_NICKNAME = "사용 가능한 닉네임입니다.";
}
