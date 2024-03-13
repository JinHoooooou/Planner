package com.kh.plan.model.Dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate2;
import com.kh.plan.model.vo.Plan;


public class PlanDao {
	
	public int insertPlan(Connection conn, String userId, String title, Date startDate, Date endDate, Date remindAlarmDate) {
		
		
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "INSERT INTO PLAN VALUES(SEQ_PLAN.NEXTVAL, ?, ?, ?, ?, SYSDATE, ?, ?)";
		
		try {
//			System.out.println(conn);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			pstmt.setString(2, title);
			
			pstmt.setDate(3, startDate);
			
			pstmt.setDate(4, endDate);
			
			pstmt.setDate(5, remindAlarmDate);
			
			pstmt.setString(6, "N");
			
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate2.close(pstmt);
			
		}
		
		return result;
	}
	
	public int updatePlan(Plan plan) {
		Connection conn = JDBCTemplate2.getConnection();
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
			JDBCTemplate2.close(pstmt);
			JDBCTemplate2.close(conn);
		}
		return result;
	}
	
	public int deletePlan(Plan plan) {
		Connection conn = JDBCTemplate2.getConnection();
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
			JDBCTemplate2.close(pstmt);
			JDBCTemplate2.close(conn);
		}
		return result;
		
	}
	
	public ArrayList<Plan> selectPlan(Connection conn, String userId) {
		ArrayList<Plan> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		// 마감일이 빠른 순으로 조회하도록 함!
		String sql = "SELECT * FROM PLAN WHERE WRITER = ? ORDER BY END_DATE";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Plan p = new Plan(rset.getInt("PLAN_ID"), rset.getString("WRITER"), rset.getString("TITLE"), rset.getDate("START_DATE"), rset.getDate("END_DATE"), rset.getDate("CREATE_DATE"), rset.getDate("REMIND_ALARM_DATE"), rset.getString("COMPLETE"));
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate2.close(rset);
			JDBCTemplate2.close(pstmt);
			
		}
		return list;
		
	}
	
	public ArrayList<Plan> searchPlanByKeyWord(String keyWord) {
		Connection conn = JDBCTemplate2.getConnection();
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
			JDBCTemplate2.close(rset);
			JDBCTemplate2.close(pstmt);
			JDBCTemplate2.close(conn);
		}
		return list;
	}
	
	
}

