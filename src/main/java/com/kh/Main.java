package com.kh;

import com.kh.model.dao.PlanDao_Minseok;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.Plan_Minseok;
import com.kh.model.vo.User;


public class Main {

  public static void main(String[] args) {
 /*   UserDao userDao = new UserDao();

    userDao.insert(User.builder()
        .userId("jinho")
        .userPw("123465")
        .userName("이진호")
        .nickname("binary")
        .email("jinho@kh.kr")
        .phone("01012345678")
        .build());

    User jinho = userDao.findByUserId("jinho");
    System.out.println(jinho);
*/    
  PlanDao_Minseok planDao = new PlanDao_Minseok();
  /*   java.sql.Date s = java.sql.Date.valueOf("2024-02-27");
    java.sql.Date e = java.sql.Date.valueOf("2024-02-28");
    java.sql.Date r = java.sql.Date.valueOf("2024-02-29");
    planDao.insert(Plan.builder()
    .writer("jinho")
    .title("제목")
    .startDate(s)
    .endDate(e)
    .remindAlarmDate(r)
    .complete("Y")
    .build());
    */
	  
  }
}
