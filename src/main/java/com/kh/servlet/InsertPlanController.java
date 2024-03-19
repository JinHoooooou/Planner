package com.kh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InsertPlanController
 */
@WebServlet("/plan/create")
public class InsertPlanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertPlanController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("null")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId");
		String title = request.getParameter("title");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String remindAlarmDate = request.getParameter("remindAlarmDate");
		Date startDate2 = Date.valueOf(startDate);
		Date endDate2 = Date.valueOf(endDate);
		Date remindAlarmDate2 = null;
		if(userId.equals("null")) {
			response.getWriter().print("유저 아이디 오류!");
			response.getWriter().close();
			return;
		}
		if(!(remindAlarmDate.equals(""))) {
		remindAlarmDate2 = Date.valueOf(remindAlarmDate);
		} else {
			remindAlarmDate2 = null;
		}
		Plan plan = new Plan();
		plan.setWriter(userId);
		plan.setTitle(title);
		plan.setStartDate(startDate2);
		plan.setEndDate(endDate2);
		plan.setRemindAlarmDate(remindAlarmDate2);
		
		int result = new PlanDao().save(plan);
		
//		ArrayList<Plan> p = new PlanService().insertPlan(userId, title, startDate2, endDate2, remindAlarmDate2);
		PrintWriter out = response.getWriter();
		if(result != 0) {
		response.sendRedirect("/plan/planInsertForm.jsp");
		} else {
			
			out.print("플랜 추가 실패!");
		}
		out.close();
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
