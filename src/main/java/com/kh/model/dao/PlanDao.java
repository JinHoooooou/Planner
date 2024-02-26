package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.database.JdbcTemplate;
import com.kh.model.vo.Plan;

public class PlanDao {

	public int insertPlan(Plan plan, Connection conn) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "INSERT INTO PLAN VALUES(SEQ_PLAN, ?, ?, ?, ?, SYSDATE, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, plan.getWriter());
			pstmt.setString(2, plan.getTitle());
			pstmt.setDate(3, plan.getStartDate());
			pstmt.setDate(4, plan.getEndDate());
			pstmt.setDate(5, plan.getRemindAlarmDate());
			pstmt.setString(6, String.valueOf(plan.getComplete()));
		
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(pstmt);
		}
		return result;
	}
	
	public int updatePlan(Plan plan, Connection conn) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "UPDATE PLAN SET TITLE = ?, END_DATE = ?, REMIND_ALARM_DATE =?, COMPLETE =? WHERE WRITER =?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, plan.getTitle());
			pstmt.setDate(2, plan.getEndDate());
			pstmt.setDate(3, plan.getRemindAlarmDate());
			pstmt.setString(4, plan.getComplete());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(pstmt);
		}
		return result;
	}
	
	public int deletePlan(Plan plan, Connection conn) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "DELETE FROM PLAN WHERE WRITER = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, plan.getWriter());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(pstmt);
		}
		return result;
		
	}
	
	public ArrayList<Plan> searchPlan(Connection conn, String writer) {
		ArrayList<Plan> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		// 마감일이 빠른 순으로 조회하도록 함!
		String sql = "SELECT * FROM PLAN WHERE WRITER = ? ORDER BY END_DATE";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, writer);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Plan p = new Plan(rset.getInt("PLAN_ID"), rset.getString("WRITER"), rset.getString("TITLE"), rset.getDate("START_DATE"), rset.getDate("END_DATE"), rset.getDate("CREATE_DATE"), rset.getDate("REMIND_ALARM_DATE"), rset.getString("COMPLETE"));
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
		}
		return list;
		
	}
	
	public ArrayList<Plan> searchPlanByKeyWord(Connection conn, String keyWord) {
		ArrayList<Plan> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		// 제목을 키워드 검색하여 조회할 수 있도록 함.
		String sql = "SELECT * FROM PLAN WHERE TITLE LIKE ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+keyWord+"%");
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Plan p = new Plan(rset.getInt("PLAN_ID"), rset.getString("WRITER"), rset.getString("TITLE"), rset.getDate("START_DATE"), rset.getDate("END_DATE"), rset.getDate("CREATE_DATE"), rset.getDate("REMIND_ALARM_DATE"), rset.getString("COMPLETE"));
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate.close(rset);
			JdbcTemplate.close(pstmt);
		}
		return list;
	}
	
	
}
