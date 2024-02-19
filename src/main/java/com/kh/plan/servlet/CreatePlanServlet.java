package com.kh.plan.servlet;

import com.kh.plan.service.PlanService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/plan/create")
public class CreatePlanServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    new PlanService().create(
        req.getParameter("title"),
        LocalDate.parse(req.getParameter("startDate")),
        LocalDate.parse(req.getParameter("endDate")));

    resp.sendRedirect("/index.html");
  }
}
