package com.kh.dao;

import com.kh.model.dao.PlanDao;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.Plan;
import com.kh.model.vo.User;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoTestUtils {

  public static UserDao userDao = new UserDao();

  public static void addUserData(String userId) {
    userDao.save(User.builder()
        .userId(userId)
        .userPw("password")
        .userPwConfirm("password")
        .userName("validUserName")
        .nickname(userId)
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
          .userPwConfirm("password")
          .userName("validUserName" + i)
          .nickname("nickname" + i)
          .email("valid" + i + "@kh.kr")
          .phone("010-1234-5678")
          .build());
    }
    return users;
  }

  public static void addPlanData(PlanDao planDao, String userId, int count) {
    for (int i = 0; i < count; i++) {
      planDao.insert(Plan.builder()
          .writer(userId)
          .title("validTitle" + i)
          .startDate(Date.valueOf(LocalDate.now()))
          .endDate(Date.valueOf(LocalDate.now()))
          .remindAlarmDate(Date.valueOf(LocalDate.now()))
          .complete(false)
          .createDate(Date.valueOf(LocalDate.now()))
          .build());
    }
  }
}
