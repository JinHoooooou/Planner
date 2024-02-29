package com.kh;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.kh.model.dao.PlanDao_Sim;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.Plan_Sim;
import com.kh.model.vo.User;

public class Main_Sim {

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
    
	 
	    
	  java.sql.Date d=  java.sql.Date.valueOf("2024-02-26");
	  
    PlanDao_Sim pd = new PlanDao_Sim();
//    Plan_Sim p = new Plan_Sim("validUserId0", "운동", d, d, d, "Y");
//    pd.insertPlan(p);
    pd.insertPlan(Plan_Sim.builder().writer("validUserId1").title("게임").startDate(d).endDate(d).remindAlarmDate(d).complete("Y").build());
    ArrayList<Plan_Sim> list = pd.searchPlan("validUserId1");
    System.out.println(list);
  }
}
