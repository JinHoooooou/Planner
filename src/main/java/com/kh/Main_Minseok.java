package com.kh;

import com.kh.model.dao.PlanDao_Minseok;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.Plan_Minseok;
import com.kh.model.vo.User;


public class Main_Minseok {

  public static void main(String[] args) {
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
