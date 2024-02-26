package com.kh.model.dao;

import com.kh.database.JdbcTemplate;
import com.kh.model.vo.Plan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlanDao {
	
	  private static final Logger LOGGER = LoggerFactory.getLogger(PlanDao.class);

	  public void insert(Plan p) {
		  try (Connection connection = JdbcTemplate.getConnection()) {
		      String sql = "INSERT INTO PLAN VALUES (SEQ_PLAN_NEXTVAL, ?, ?, ?, ?, SYSDATE, ?, ?)";
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
	public void update(Plan p) {
		try (Connection connection = JdbcTemplate.getConnection()) {
			String sql = "UPDATE SET TITLE = ?, START_DATE = ?, END_DATE = ?, REMIND_ALARM_DATE = ?, COMPLETE = ? WHERE WRITER = ?";
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
	public void delete(Plan p) {
		try (Connection connection = JdbcTemplate.getConnection()) {
			String sql = "DELETE FROM PLAN WHERE = ?";
			  PreparedStatement pstmt = connection.prepareStatement(sql);
			  pstmt.setInt(1, p.getPlanId());
			  
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

