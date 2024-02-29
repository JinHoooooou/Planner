package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.database.JdbcTemplate_Sim;
import com.kh.model.vo.Plan_Sim;


public class PlanDao_Sim {
	
	public int insertPlan(Plan_Sim p) {
		
		Connection conn = JdbcTemplate_Sim.getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "INSERT INTO PLAN VALUES(SEQ_PLAN.NEXTVAL, ?, ?, ?, ?, SYSDATE, ?, ?)";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, p.getWriter());
			
			pstmt.setString(2, p.getTitle());
			
			pstmt.setDate(3, p.getStartDate());
			
			pstmt.setDate(4, p.getEndDate());
			
			pstmt.setDate(5, p.getRemindAlarmDate());
			
			pstmt.setString(6, p.getComplete());
			
			
			result = pstmt.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate_Sim.close(pstmt);
			JdbcTemplate_Sim.close(conn);
		}
		
		return result;
	}
	
	public int updatePlan(Plan_Sim plan) {
		Connection conn = JdbcTemplate_Sim.getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "UPDATE PLAN SET TITLE = ?, END_DATE = ?, REMIND_ALARM_DATE =?, COMPLETE =? WHERE PLAN_ID =? AND WRITER =?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, plan.getTitle());
			pstmt.setDate(2, plan.getEndDate());
			pstmt.setDate(3, plan.getRemindAlarmDate());
			pstmt.setString(4, plan.getComplete());
			pstmt.setInt(5, plan.getPlanId());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate_Sim.close(pstmt);
			JdbcTemplate_Sim.close(conn);
		}
		return result;
	}
	
	public int deletePlan(Plan_Sim plan) {
		Connection conn = JdbcTemplate_Sim.getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "DELETE FROM PLAN WHERE PLAN_ID = ? AND WRITER = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, plan.getPlanId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate_Sim.close(pstmt);
			JdbcTemplate_Sim.close(conn);
		}
		return result;
		
	}
	
	public ArrayList<Plan_Sim> searchPlan(String writer) {
		Connection conn = JdbcTemplate_Sim.getConnection();
		ArrayList<Plan_Sim> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		// 마감일이 빠른 순으로 조회하도록 함!
		String sql = "SELECT * FROM PLAN WHERE WRITER = ? ORDER BY END_DATE";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, writer);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Plan_Sim p = new Plan_Sim(rset.getInt("PLAN_ID"), rset.getString("WRITER"), rset.getString("TITLE"), rset.getDate("START_DATE"), rset.getDate("END_DATE"), rset.getDate("CREATE_DATE"), rset.getDate("REMIND_ALARM_DATE"), rset.getString("COMPLETE"));
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate_Sim.close(rset);
			JdbcTemplate_Sim.close(pstmt);
			JdbcTemplate_Sim.close(conn);		
		}
		return list;
		
	}
	
	public ArrayList<Plan_Sim> searchPlanByKeyWord(String keyWord) {
		Connection conn = JdbcTemplate_Sim.getConnection();
		ArrayList<Plan_Sim> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		// 제목을 키워드 검색하여 조회할 수 있도록 함.
		String sql = "SELECT * FROM PLAN WHERE TITLE LIKE ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+keyWord+"%");
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Plan_Sim p = new Plan_Sim(rset.getInt("PLAN_ID"), rset.getString("WRITER"), rset.getString("TITLE"), rset.getDate("START_DATE"), rset.getDate("END_DATE"), rset.getDate("CREATE_DATE"), rset.getDate("REMIND_ALARM_DATE"), rset.getString("COMPLETE"));
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcTemplate_Sim.close(rset);
			JdbcTemplate_Sim.close(pstmt);
			JdbcTemplate_Sim.close(conn);
		}
		return list;
	}
	
	
}
