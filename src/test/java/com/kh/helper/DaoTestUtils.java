package com.kh.helper;

import com.kh.model.dao.PlanDao;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.Plan;
import com.kh.model.vo.User;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DaoTestUtils {

  public static UserDao userDao = new UserDao();
  public static PlanDao planDao = new PlanDao();

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

  public static void addPlanData(String userId, String title, int count) {
    for (int i = 0; i < count; i++) {
      planDao.save(Plan.builder()
          .writer(userId)
          .title(title + (i + 1))
          .startDate(Date.valueOf(LocalDate.now()))
          .endDate(Date.valueOf(LocalDate.now()))
          .remindAlarmDate(Date.valueOf(LocalDate.now()))
          .complete(false)
          .createDate(Date.valueOf(LocalDate.now()))
          .build());
    }
  }

  public static void addPlanDataDifferentEndDate(String userId, String title, int count) {
    for (int i = 0; i < count; i++) {
      int month = new Random().nextInt(1, 13);
      int days = new Random().nextInt(1, 30);
      if (month == 2 && days == 30) {
        days = 29;
      }
      planDao.save(Plan.builder()
          .writer(userId)
          .title(title + (i + 1))
          .startDate(Date.valueOf(LocalDate.now()))
          .endDate(Date.valueOf(LocalDate.parse(String.format("2024-%02d-%02d", month, days))))
          .remindAlarmDate(Date.valueOf(LocalDate.now()))
          .complete(false)
          .createDate(Date.valueOf(LocalDate.now()))
          .build());
    }
  }
}
