package com.kh;

import java.util.ArrayList;
import java.util.List;

import com.kh.model.dao.DetailPlanDao_Sim;
import com.kh.model.dao.PlanDao_Sim;
import com.kh.model.vo.DetailPlan_Sim;
import com.kh.model.vo.Plan_Sim;

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
    
	 
	    
	  java.sql.Date d=  java.sql.Date.valueOf("2024-03-04");
//	  
//    PlanDao_Sim pd = new PlanDao_Sim();
//    Plan_Sim p = new Plan_Sim("validUserId0", "운동", d, d, d, "Y");
//    pd.insertPlan(p);
//    pd.insertPlan(Plan_Sim.builder().writer("validUserId1").title("게임").startDate(d).endDate(d).remindAlarmDate(d).complete("Y").build());
//    ArrayList<Plan_Sim> list = pd.searchPlan("validUserId1");
//    System.out.println(list);

    DetailPlanDao_Sim dpd = new DetailPlanDao_Sim();
    
//    dpd.insert(DetailPlan_Sim.builder().planId(2).writer("validUserId1").contents("나는 오늘 롤을 할거야").startTime(d).endTime(d).remindAlarmTime(d).complete("N").build());
    List<DetailPlan_Sim> list2 = dpd.findByContentsContaining("롤");
    
    System.out.println(list2);
  }
}
