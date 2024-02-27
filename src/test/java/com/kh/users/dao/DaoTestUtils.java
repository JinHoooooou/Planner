package com.kh.users.dao;

import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import java.util.ArrayList;
import java.util.List;

public class DaoTestUtils {

  public static void addUserData(UserDao userDao) {
    userDao.insert(User.builder()
        .userId("validUserId")
        .userPw("123456")
        .userName("validUserName")
        .nickname("validNickname")
        .email("valid@kh.kr")
        .phone("0101234567")
        .build());
  }

  public static List<User> buildValidUsers(int count) {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      users.add(User.builder()
          .userId("validUserId" + i)
          .userPw("password")
          .userName("validUserName" + i)
          .nickname("nickname" + i)
          .email("valid" + i + "@kh.kr")
          .phone("010-1234-5678")
          .build());

    }
    return users;
  }
}
