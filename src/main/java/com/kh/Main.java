package com.kh;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.kh.model.dao.PlanDao;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.Plan;
import com.kh.model.vo.User;

public class Main {

  public static void main(String[] args) {
//    UserDao userDao = new UserDao();
//
//    userDao.insert(User.builder()
//        .userId("jinho")
//        .userPw("123465")
//        .userName("이진호")
//        .nickname("binary")
//        .email("jinho@kh.kr")
//        .phone("01012345678")
//        .build());
//
//    User jinho = userDao.findByUserId("jinho");
//    System.out.println(jinho);
    
	  
	    
	  java.sql.Date d=  java.sql.Date .valueOf("2024-02-26");
	  
    PlanDao pd = new PlanDao();
    pd.insertPlan(Plan.builder().writer("sim").title("운동").startDate(d).endDate(d).remindAlarmDate(d).complete("Y").build());
    ArrayList<Plan> list = pd.searchPlan("sim");
    System.out.println(list);
  }
}
