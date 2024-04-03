package com.kh.constant;

public interface Regex {

  String USER_ID = "^[A-Za-z][A-Za-z0-9_]{7,16}$";
  String USER_NICKNAME = "^[가-힣a-zA-Z0-9]{3,20}$";
}
