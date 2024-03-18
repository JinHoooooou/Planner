package com.kh.model.dao;

import com.kh.database.JdbcTemplate;
import com.kh.database.KeyHolder;
import com.kh.database.RowMapper;
import com.kh.model.vo.DetailPlan;
import java.util.List;

public class DetailPlanDao {

  public DetailPlan save(DetailPlan detailPlan) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();
    String query = """
        INSERT INTO DETAIL_PLAN(DETAIL_PLAN_ID, PLAN_ID, WRITER, CONTENTS,
         START_TIME, END_TIME, REMIND_ALARM_TIME, COMPLETE)
        VALUES(?, ?, ?, ?, ?, ?, ?, ?)
        """;

    KeyHolder keyHolder = new KeyHolder();
    keyHolder.setId(jdbctemplate.getNextVal());

    jdbctemplate.executeUpdate(query,
        keyHolder.getId(), detailPlan.getPlanId(), detailPlan.getWriter(), detailPlan.getContents(),
        detailPlan.getStartTime(), detailPlan.getEndTime(), detailPlan.getRemindAlarmTime(),
        detailPlan.isComplete() ? "Y" : "N");

    return findByDetailPlanId(keyHolder.getId());
  }

  public int update(DetailPlan detailPlan) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();

    String query = """
        UPDATE DETAIL_PLAN SET
        CONTENTS = ?, START_TIME = ?, END_TIME = ?, REMIND_ALARM_TIME = ?, COMPLETE = ?
        WHERE DETAIL_PLAN_ID = ? AND WRITER = ? AND PLAN_ID=?
        """;

    return jdbctemplate.executeUpdate(query,
        detailPlan.getContents(), detailPlan.getStartTime(), detailPlan.getEndTime(),
        detailPlan.getRemindAlarmTime(), detailPlan.isComplete() ? "Y" : "N",
        detailPlan.getDetailPlanId(), detailPlan.getWriter(), detailPlan.getPlanId());
  }

  public int deleteByDetailPlanIdAndPlanIdAndWriter(int detailPlanId, int planId, String writer) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();

    String query = """
        DELETE FROM DETAIL_PLAN
        WHERE DETAIL_PLAN_ID = ? AND WRITER = ? AND PLAN_ID =?
        """;
    return jdbctemplate.executeUpdate(query, detailPlanId, writer, planId);
  }

  public List<DetailPlan> findAll() {
    JdbcTemplate jdbctemplate = new JdbcTemplate();

    String query = "SELECT * FROM DETAIL_PLAN";
    RowMapper<DetailPlan> mapper = DetailPlan::from;
    return jdbctemplate.executeQuery(query, mapper);
  }

  public List<DetailPlan> findByWriter(String writer) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();

    String query = "SELECT * FROM DETAIL_PLAN WHERE WRITER = ?";
    RowMapper<DetailPlan> mapper = DetailPlan::from;
    return jdbctemplate.executeQuery(query, mapper, writer);
  }

  public List<DetailPlan> findByWriterAndPlanId(String writer, int planId) {
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
