package com.kh.Servlet;

import java.io.IOException;
import java.util.List;

import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class DeletePlanController
 */
@WebServlet("/delete.pl")
public class DeletePlanServlet extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId");
		String planIdS = request.getParameter("planId");
		int planId = Integer.parseInt(planIdS);
		int result = new PlanDao().deleteByPlanIdAndWriter(planId, userId);
		if(result != 0) {
		List<Plan> list = new PlanDao().findByWriter(userId);
		HttpSession session = request.getSession();
		session.setAttribute("planList", list);
		response.sendRedirect("/plan/showPlanForm.jsp");
		} else {
			response.getWriter().print("삭제 실패했어요 ㅠㅠ");
		}
		
	}



}
