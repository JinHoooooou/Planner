package com.kh.servlet;

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
 * Servlet implementation class ShowPlanController
 */
@WebServlet("/plan/list")
public class ShowPlanServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		List<Plan> p = new PlanDao().findByWriter(userId);
		HttpSession session = request.getSession();
		session.setAttribute("planList", p);
		session.setAttribute("userId", userId);
		response.sendRedirect("/plan/showPlanForm.jsp");
	}



}
