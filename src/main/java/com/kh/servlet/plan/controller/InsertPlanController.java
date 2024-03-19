package com.kh.servlet.plan.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.servlet.plan.model.vo.Plan;
import com.kh.servlet.plan.service.PlanService;

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
		System.out.println(remindAlarmDate2);
		
		ArrayList<Plan> p = new PlanService().insertPlan(userId, title, startDate2, endDate2, remindAlarmDate2);
		

		response.sendRedirect(request.getContextPath());
		
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
