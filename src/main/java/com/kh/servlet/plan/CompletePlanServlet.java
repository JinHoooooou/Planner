package com.kh.servlet.plan;

import java.io.IOException;

import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CompletePlanController
 */
@WebServlet("/complete.pl")
public class CompletePlanServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId");
		String planIdS = request.getParameter("planId");
		int planId = Integer.parseInt(planIdS);
		Plan p = new Plan();
		p.setWriter(userId);
		p.setPlanId(planId);
		int result = new PlanDao().completePlan(p);
		PrintWriter out = response.getWriter();
		if(result != 0) {
		List<Plan> list = new PlanDao().findByWriter(userId);
		HttpSession session = request.getSession();
		session.setAttribute("planList", list);
		response.sendRedirect("/plan/showPlanForm.jsp");
		} else {
			response.setStatus(400);
		}
//		if(result != 0) {
//		List<Plan> list = new PlanDao().findByWriter(userId);
//		HttpSession session = request.getSession();
//		session.setAttribute("planList", list);
//		response.sendRedirect("/plan/showPlanForm.jsp");
//		} else {
//			out.print("플랜 완료 체크 실패");
//		}
//		out.close();

	}



}
