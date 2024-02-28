package com.kh.model.dao;

import com.kh.database.JdbcTemplate;
import com.kh.database.RowMapper;
import com.kh.model.vo.Plan;
import java.util.List;

public class PlanDao {

  public void insert(Plan plan) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query =
        "INSERT INTO PLAN(PLAN_ID, WRITER, TITLE, START_DATE, END_DATE, REMIND_ALARM_DATE, COMPLETE)"
            + " VALUES(SEQ_PLAN.NEXTVAL, ?, ?, ?, ?, ?, ?)";
    jdbcTemplate.executeUpdate(query, plan.getWriter(), plan.getTitle(), plan.getStartDate(),
        plan.getEndDate(), plan.getRemindAlarmDate(), plan.isComplete());
  }

  public void update(Plan update) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "UPDATE PLAN SET TITLE=?, END_DATE=?, REMIND_ALARM_DATE=?, COMPLETE=? WHERE (WRITER=? AND PLAN_ID=?)";
    jdbcTemplate.executeUpdate(query, update.getTitle(), update.getEndDate(),
        update.getRemindAlarmDate(), update.isComplete(), update.getWriter(), update.getPlanId());
  }

  public void delete(Plan plan) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "DELETE FROM PLAN WHERE (WRITER=? AND PLAN_ID=?)";
    jdbcTemplate.executeUpdate(query, plan.getWriter(), plan.getPlanId());
  }

  public List<Plan> findByUsersIdOrderByEndDate(String writer) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "SELECT * FROM PLAN WHERE WRITER = ? ORDER BY END_DATE";
    RowMapper<Plan> mapper = Plan::from;
    return jdbcTemplate.executeQuery(query, mapper, writer);
  }

  public List<Plan> findByTitleContaining(String titleKeyword) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    String query = "SELECT * FROM PLAN WHERE TITLE LIKE ?";
    RowMapper<Plan> mapper = Plan::from;
    return jdbcTemplate.executeQuery(query, mapper, "%" + titleKeyword + "%");
  }
}

