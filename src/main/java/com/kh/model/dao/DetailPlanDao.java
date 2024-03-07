package com.kh.model.dao;

import com.kh.database.JdbcTemplate;
import com.kh.database.RowMapper;
import com.kh.model.vo.DetailPlan;
import java.util.List;

public class DetailPlanDao {

  public void insert(DetailPlan detailPlan) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();

    String query =
        "INSERT INTO DETAIL_PLAN(DETAIL_PLAN_ID, PLAN_ID, WRITER, CONTENTS, START_TIME, END_TIME, REMIND_ALARM_TIME, COMPLETE)"
            + " VALUES(SEQ_DETAIL, ?, ?, ?, ?, ?, SYSDATE, ?)";
    jdbctemplate.executeUpdate(query,
        detailPlan.getPlanId(), detailPlan.getWriter(), detailPlan.getContents(),
        detailPlan.getStartTime(), detailPlan.getEndTime(), detailPlan.getRemindAlarmTime(),
        detailPlan.getCreateDate(), detailPlan.getComplete());
  }

  public void update(DetailPlan detailPlan) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();

    String query =
        "UPDATE DETAIL_PLAN SET CONTENTS = ?, START_TIME = ?, END_TIME = ?, REMIND_ALARM_TIME = ?, COMPLETE = ?"
            + "WHERE DETAIL_PLAN_ID = ? AND WRITER = ?";
    jdbctemplate.executeUpdate(query,
        detailPlan.getContents(), detailPlan.getStartTime(), detailPlan.getEndTime(),
        detailPlan.getRemindAlarmTime(), detailPlan.getComplete(), detailPlan.getDetailPlanId(),
        detailPlan.getWriter());
  }

  public void delete(DetailPlan detailPlan) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();

    String query = "DELETE FROM DETAIL_PLAN WHERE DETAIL_PLAN_ID = ? AND WRITER = ?";
    jdbctemplate.executeUpdate(query, detailPlan.getDetailPlanId(), detailPlan.getWriter());
  }

  public List<DetailPlan> findByUserIdAndPlanId(String writer, int planId) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();

    String query = "SELECT * FROM DETAIL_PLAN WHERE WRITER = ? AND PLAN_ID=?";
    RowMapper<DetailPlan> mapper = DetailPlan::from;
    return jdbctemplate.executeQuery(query, mapper, writer, planId);
  }

  public DetailPlan findByDetailPlanId(int detailPlanId) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();

    String query = "SELECT * FROM DETAIL_PLAN WHERE DETAIL_PLAN_ID=?";
    RowMapper<DetailPlan> mapper = DetailPlan::from;
    return jdbctemplate.executeQueryForOne(query, mapper, detailPlanId);
  }

}
