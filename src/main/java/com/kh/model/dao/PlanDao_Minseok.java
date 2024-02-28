package com.kh.model.dao;

import com.kh.database.JdbcTemplate_Minseok;
import com.kh.model.vo.Plan_Minseok;
import com.kh.model.vo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlanDao_Minseok {
	
	  private static final Logger LOGGER = LoggerFactory.getLogger(PlanDao_Minseok.class);

	  public void insert(Plan_Minseok p) {
		  try (Connection connection = JdbcTemplate_Minseok.getConnection()) {
		      String sql = "INSERT INTO PLAN VALUES (SEQ_PLAN.NEXTVAL, ?, ?, ?, ?, SYSDATE, ?, ?)";
		      PreparedStatement pstmt = connection.prepareStatement(sql);
		      pstmt.setString(1, p.getWriter());
		      pstmt.setString(2, p.getTitle());
		      pstmt.setDate(3, p.getStartDate());
		      pstmt.setDate(4, p.getEndDate());
		      pstmt.setDate(5, p.getRemindAlarmDate());
		      pstmt.setString(6, p.getComplete());

		      int result = pstmt.executeUpdate();
		      if (result > 0) {
		        connection.commit();
		      } else {
		        connection.rollback();
		      }
		    } catch (SQLException e) {
		      LOGGER.error(e.getMessage());
		    }
		  } 
	  public Plan_Minseok findByWriter(String writer) {
		    try (Connection connection = JdbcTemplate_Minseok.getConnection()) {
		      String sql = "SELECT * FROM PLAN WHERE WRITER = ?";
		      PreparedStatement pstmt = connection.prepareStatement(sql);
		      pstmt.setString(1, writer);
		      ResultSet resultSet = pstmt.executeQuery();

		      if (resultSet.next()) {
		        return Plan_Minseok.from(resultSet);
		      }
		    } catch (SQLException e) {
		      LOGGER.error(e.getMessage());
		    }
		    return Plan_Minseok.builder().build();
		  }
	public void update(Plan_Minseok p) {
		try (Connection connection = JdbcTemplate_Minseok.getConnection()) {
			String sql = "UPDATE PLAN SET TITLE = ?, START_DATE = ?, END_DATE = ?, REMIND_ALARM_DATE = ?, COMPLETE = ? WHERE WRITER = ?";
			  PreparedStatement pstmt = connection.prepareStatement(sql);
		      pstmt.setString(1, p.getTitle());
		      pstmt.setDate(2, p.getStartDate());
		      pstmt.setDate(3, p.getEndDate());
		      pstmt.setDate(4, p.getRemindAlarmDate());
		      pstmt.setString(5, p.getComplete());
		      pstmt.setString(6, p.getWriter());
		      
		      int result = pstmt.executeUpdate();
		      if (result > 0) {
		        connection.commit();
		      } else {
		        connection.rollback();
		      }
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
	}
	public void delete(Plan_Minseok p) {
		try (Connection connection = JdbcTemplate_Minseok.getConnection()) {
			String sql = "DELETE FROM PLAN WHERE TITLE = ?";
			  PreparedStatement pstmt = connection.prepareStatement(sql);
			  pstmt.setString(1, p.getTitle());
			  
			  int result = pstmt.executeUpdate();
		      if (result > 0) {
		        connection.commit();
		      } else {
		        connection.rollback();
		      }
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
	}
}

