package com.kh.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.kh.database.JdbcTemplate;
import com.kh.database.JdbcTemplate_Sim2;
import com.kh.database.RowMapper;
import com.kh.model.vo.DetailPlan_Sim;
import com.kh.model.vo.Plan;

public class DetailPlanDao_Sim {

	public int insert(DetailPlan_Sim dPlan) {
		JdbcTemplate_Sim2 jdbctemplate = new JdbcTemplate_Sim2();
		int result = 0;
		String query = "INSERT INTO DETAIL_PLAN(DETAIL_PLAN_ID, PLAN_ID, WRITER, CONTENTS, START_TIME, END_TIME, REMIND_ALARM_TIME, CREATE_DATE, COMPLETE)"
						+ " VALUES(SEQ_DETAIL.NEXTVAL, ?, ?, ?, ?, ?, ?, SYSDATE, ?)";
		result = jdbctemplate.executeUpdate(query, dPlan.getPlanId(), dPlan.getWriter(), dPlan.getContents(), 
								dPlan.getStartTime(), dPlan.getEndTime(), dPlan.getRemindAlarmTime(), 
								dPlan.getComplete());
		
		return result;		
	}
	public int update(DetailPlan_Sim dPlan) {
		JdbcTemplate_Sim2 jdbctemplate = new JdbcTemplate_Sim2();
		int result = 0;
		
		String query = "UPDATE DETAIL_PLAN SET CONTENTS = ?, START_TIME = ?, END_TIME = ?, REMIND_ALARM_TIME = ?, COMPLETE = ?"
						+ "WHERE DETAIL_PLAN_ID = ? AND WRITER = ?";
		jdbctemplate.executeUpdate(query, dPlan.getContents(), dPlan.getStartTime(), dPlan.getEndTime(),
							dPlan.getRemindAlarmTime(), dPlan.getComplete(), dPlan.getDetailPlanId(), dPlan.getWriter());
		return result;
	}
	public int delete(DetailPlan_Sim dPlan) {
		JdbcTemplate_Sim2 jdbctemplate = new JdbcTemplate_Sim2();
		int result = 0;
		
		String query = "DELETE FROM DETAIL_PLAN WHERE DETAIL_PLAN_ID = ? AND WRITER = ?";
		jdbctemplate.executeUpdate(query, dPlan.getDetailPlanId(), dPlan.getWriter());
		
		return result;
	}
	public List<DetailPlan_Sim> findByUserIdOrderByEndTime(String writer) {
		JdbcTemplate_Sim2 jdbctemplate = new JdbcTemplate_Sim2();
//		List<DetailPlan_Sim> list = new ArrayList<>();
		
		String query = "SELECT * FROM DETAIL_PLAN WHERE WRITER = ? ORDER BY END_TIME";
		RowMapper<DetailPlan_Sim> mapper = DetailPlan_Sim::from;
		return jdbctemplate.executeQuery(query, mapper, writer);
	}
	
	 public List<DetailPlan_Sim> findByContentsContaining(String contentsKeyWord) {
		    JdbcTemplate jdbcTemplate = new JdbcTemplate();
		    String query = "SELECT * FROM DETAIL_PLAN WHERE CONTENTS LIKE ?";
		    RowMapper<DetailPlan_Sim> mapper = DetailPlan_Sim::from;
		    return jdbcTemplate.executeQuery(query, mapper, "%" + contentsKeyWord + "%");
		  }
	
}
