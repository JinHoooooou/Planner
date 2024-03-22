package com.kh.Servlet;

import java.io.IOException;
import java.util.List;

import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class SearchTitleController
 */
@WebServlet("/search.pl")
public class SearchTitleServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String keyWord = request.getParameter("search");
		String userId = request.getParameter("userId");
		List<Plan> list = new PlanDao().findByWriterAndTitleContaining(userId, keyWord);
		HttpSession session = request.getSession();
		session.setAttribute("planListKeyWord", list);

		RequestDispatcher view = request.getRequestDispatcher("plan/planSearchKeywordForm.jsp");
		view.forward(request, response);
		
	}



}
