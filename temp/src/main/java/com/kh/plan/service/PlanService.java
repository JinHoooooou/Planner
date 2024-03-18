package com.kh.plan.service;


import static com.kh.common.JDBCTemplate2.close;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate2;
import com.kh.plan.model.Dao.PlanDao;
import com.kh.plan.model.vo.Plan;

public class PlanService {

	public ArrayList<Plan> insertPlan(String userId, String title, Date startDate, Date endDate, Date remindAlarmDate) {
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate2.getConnection();
//		System.out.println(conn);
		// 2) Dao 에게 데이터 전달과 함께 필요한 메소드 호출
		int result = new PlanDao().insertPlan(conn, userId, title, startDate, endDate, remindAlarmDate);
		ArrayList<Plan> list = null;
		// 3) DML 사용 시 트랜잭션 처리
		if(result >0) {
			JDBCTemplate2.commit(conn);
			list = new PlanDao().selectPlan(conn, userId);
		} else {
			JDBCTemplate2.rollback(conn);
		}
		
		// 4) Connection 객체 반납(close)
		close(conn);
		
		return list;
	}
	
	
	public ArrayList<Plan> searchPlanByKeyWord(String keyWord) {
		Connection conn = JDBCTemplate2.getConnection();
		
		ArrayList<Plan> list = new PlanDao().searchPlanByKeyWord(keyWord);
		
		close(conn);
		return list;
	}
	
	public ArrayList<Plan> deletePlan(String userId, int planId) {
		Connection conn = JDBCTemplate2.getConnection();
		
		int result = new PlanDao().deletePlan(conn, userId, planId);
		
		ArrayList<Plan> list = null;
		if(result > 0) {
			JDBCTemplate2.commit(conn);
			list = new PlanDao().selectPlan(conn, userId);
		} else {
			JDBCTemplate2.rollback(conn);
		}
		
		close(conn);
		
		return list;
	}
	
	public ArrayList<Plan> completePlan(String userId, int planId) {
		Connection conn = JDBCTemplate2.getConnection();
		
		int result = new PlanDao().completePlan(conn, userId, planId);
		
		ArrayList<Plan> list = null;
		
		if(result > 0) {
			JDBCTemplate2.commit(conn);
			list = new PlanDao().selectPlan(conn, userId);
		} else {
			JDBCTemplate2.rollback(conn);
		}
		close(conn);
		
		return list;
	}
	
	public ArrayList<Plan> showPlan(String userId) {
		Connection conn = JDBCTemplate2.getConnection();
		
		ArrayList<Plan> p = new PlanDao().selectPlan(conn, userId);
		
		close(conn);
		
		return p;
	}
}
