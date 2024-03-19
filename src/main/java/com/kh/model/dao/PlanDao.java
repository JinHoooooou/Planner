package com.kh.model.dao;

import com.kh.database.JdbcTemplate;
import com.kh.database.RowMapper;
import com.kh.model.vo.Plan;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {

  public int save(Plan plan) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "INSERT INTO PLAN(PLAN_ID, WRITER, TITLE, START_DATE, END_DATE, REMIND_ALARM_DATE, COMPLETE) VALUES(SEQ_PLAN.NEXTVAL, ?, ?, ?, ?, ?, ?)";
    return jdbcTemplate.executeUpdate(query, plan.getWriter(), plan.getTitle(), plan.getStartDate(),
        plan.getEndDate(), plan.getRemindAlarmDate(), "N");
  }

  public int update(Plan update) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "UPDATE PLAN SET TITLE=?, START_DATE=?, END_DATE=?, REMIND_ALARM_DATE=?, COMPLETE=? WHERE (WRITER=? AND PLAN_ID=?)";
    return jdbcTemplate.executeUpdate(query,
        update.getTitle(), update.getStartDate(), update.getEndDate(),
        update.getRemindAlarmDate(), update.getComplete(), update.getWriter(),
        update.getPlanId());
  }
  
	public int completePlan(Plan complete) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();

		String query = """ 
				update plan set complete = 'Y' where WRITER = ? and plan_ID = ?""";
		return jdbcTemplate.executeUpdate(query, complete.getWriter(), complete.getPlanId());

	}

  public int deleteByPlanIdAndWriter(int planId, String writer) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "DELETE FROM PLAN WHERE (PLAN_ID=? AND WRITER=?)";
    return jdbcTemplate.executeUpdate(query, planId, writer);
  }

  public List<Plan> findAll() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "SELECT * FROM PLAN";
    RowMapper<Plan> mapper = Plan::from;
    return jdbcTemplate.executeQuery(query, mapper);
  }

  public Plan findByPlanId(int planId) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "SELECT * FROM PLAN WHERE PLAN_ID=?";
    RowMapper<Plan> mapper = Plan::from;
    return jdbcTemplate.executeQueryForOne(query, mapper, planId);
  }

  public List<Plan> findByWriter(String writer) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "SELECT * FROM PLAN WHERE WRITER = ?";
    RowMapper<Plan> mapper = Plan::from;
    return jdbcTemplate.executeQuery(query, mapper, writer);
  }

  public List<Plan> findByWriterAndTitleContaining(String writer, String titleKeyword) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "SELECT * FROM PLAN WHERE WRITER=? AND TITLE LIKE ?";
    RowMapper<Plan> mapper = Plan::from;
    return jdbcTemplate.executeQuery(query, mapper, writer, "%" + titleKeyword + "%");
  }

  public List<Plan> findByWriterOrderByEndDate(String writer) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "SELECT * FROM PLAN WHERE WRITER = ? ORDER BY END_DATE";
    RowMapper<Plan> mapper = Plan::from;
    return jdbcTemplate.executeQuery(query, mapper, writer);
  }
  
  
  


}
