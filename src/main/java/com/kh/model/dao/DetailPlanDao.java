package com.kh.model.dao;

import com.kh.database.JdbcTemplate;
import com.kh.database.KeyHolder;
import com.kh.database.RowMapper;
import com.kh.model.dto.detail.CreateDetailRequestDto;
import com.kh.model.vo.DetailPlan;
import java.util.List;

public class DetailPlanDao {

  public DetailPlan save(CreateDetailRequestDto requestDto, String userId) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();
    String query = "INSERT INTO DETAIL_PLAN(DETAIL_PLAN_ID, PLAN_ID, WRITER, CONTENTS,"
        + " START_TIME, END_TIME)"
        + " VALUES( ?, ?, ?, ?, ?, ?) ";

    KeyHolder keyHolder = new KeyHolder();
    keyHolder.setId(jdbctemplate.getNextVal("SEQ_DETAIL"));

    jdbctemplate.executeUpdate(query,
        keyHolder.getId(), requestDto.getPlanId(), userId, requestDto.getContents(),
        requestDto.getStartTime(), requestDto.getEndTime());

    return findByDetailPlanIdAndWriter(keyHolder.getId(), userId);
  }

  public int update(DetailPlan detailPlan) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();

    String query = "UPDATE DETAIL_PLAN "
        + "SET CONTENTS=?, START_TIME=?, END_TIME=?, REMIND_ALARM_TIME=?, COMPLETE=? "
        + "WHERE DETAIL_PLAN_ID=? AND WRITER=? AND PLAN_ID=?";

    return jdbctemplate.executeUpdate(query,
        detailPlan.getContents(), detailPlan.getStartTime(), detailPlan.getEndTime(),
        detailPlan.getRemindAlarmTime(), detailPlan.getComplete(),
        detailPlan.getDetailPlanId(), detailPlan.getWriter(), detailPlan.getPlanId());
  }

  public int updateCompleteByDetailPlanIdAndWriter(String complete, int detailPlanId, String writer) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();
    String query = "UPDATE DETAIL_PLAN SET COMPLETE = ? WHERE DETAIL_PLAN_ID = ? AND WRITER = ?";

    return jdbctemplate.executeUpdate(query, complete, detailPlanId, writer);
  }

  public int updateCompleteByPlanIdAndWriter(String complete, int planId, String writer) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();
    String query = "UPDATE DETAIL_PLAN SET COMPLETE=? WHERE PLAN_ID=? AND WRITER=?";

    return jdbctemplate.executeUpdate(query, complete, planId, writer);
  }

  public int deleteByDetailPlanIdAndWriter(int detailPlanId, String writer) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();
    String query = "DELETE FROM DETAIL_PLAN WHERE DETAIL_PLAN_ID=? AND WRITER=?";
    return jdbctemplate.executeUpdate(query, detailPlanId, writer);
  }

  public List<DetailPlan> findByPlanIdOrderByDetailPlanId(int planId) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();

    String query = "SELECT * FROM DETAIL_PLAN WHERE AND PLAN_ID=? ORDER BY DETAIL_PLAN_ID";
    RowMapper<DetailPlan> mapper = DetailPlan::from;
    return jdbctemplate.executeQuery(query, mapper, planId);
  }

  public DetailPlan findByDetailPlanIdAndWriter(int detailPlanId, String writer) {
    JdbcTemplate jdbctemplate = new JdbcTemplate();

    String query = "SELECT * FROM DETAIL_PLAN WHERE DETAIL_PLAN_ID=? AND WRITER=?";
    RowMapper<DetailPlan> mapper = DetailPlan::from;
    return jdbctemplate.executeQueryForOne(query, mapper, detailPlanId, writer);
  }
}
