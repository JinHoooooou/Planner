package com.kh.plan.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.plan.model.vo.Plan;
import com.kh.plan.service.PlanService;

/**
 * Servlet implementation class InsertPlanController
 */
@WebServlet("/insert.pl")
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId");
		String title = request.getParameter("title");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String remindAlarmDate = request.getParameter("remindAlarmDate");
		
		Date startDate2 = Date.valueOf(startDate);
		Date endDate2 = Date.valueOf(endDate);
		Date remindAlarmDate2 = null;
		if(remindAlarmDate != null || remindAlarmDate.equals("")) {
		remindAlarmDate2 = Date.valueOf(remindAlarmDate);
		}
		ArrayList<Plan> p = new PlanService().insertPlan(userId, title, startDate2, endDate2, remindAlarmDate2);
//		for(int i=0; i<p.size(); i++) {
//			System.out.println(p.get(i).getTitle());
//		}
		HttpSession session = request.getSession();
		session.setAttribute("planList", p);
		session.setAttribute("userId", userId);
		response.sendRedirect(request.getContextPath());
		
		
//		Plan p = new Plan("validUserId0", title, startDate2, endDate2, remindAlarmDate2, "Y");
//		System.out.println(p.getWriter());
//		System.out.println(p.getTitle());
//		System.out.println(p.getStartDate());
//		System.out.println(p.getEndDate());
//		System.out.println(p.getRemindAlarmDate());
//		System.out.println(p.getComplete());
//		int result = new PlanService().insertPlan(p);
//		RequestDispatcher view = request.getRequestDispatcher("views/plan/planInsertForm.jsp");
//		if(result<=0) {
//			 
//		} else {
//			view.forward(request, response);
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
