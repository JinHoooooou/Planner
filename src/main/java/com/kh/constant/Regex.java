package com.kh.constant;

public interface Regex {

  String USER_ID = "^[A-Za-z][A-Za-z0-9_]{7,16}$";
  String USER_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_=+]).{8,20}$";
  String USER_NICKNAME = "^[가-힣a-zA-Z0-9]{3,20}$";
  String USER_NAME = "^[가-힣]{2,20}$";
  String USER_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
  String USER_PHONE = "^01[0-9][0-9]{3,4}[0-9]{4}$";
}
