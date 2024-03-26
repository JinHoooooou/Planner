package com.kh.servlet.plan;

import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

/**
 * Servlet implementation class InsertPlanController
 */
@WebServlet("/plan/create")
public class CreatePlanServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    response.setCharacterEncoding("utf-8");
    HttpSession session = request.getSession();
    String userId = (String)session.getAttribute("userId");
    String title = request.getParameter("title");
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    String remindAlarmDate = request.getParameter("remindAlarmDate");
    Date startDate2 = Date.valueOf(startDate);
    Date endDate2 = Date.valueOf(endDate);
    Date remindAlarmDate2 = null;
    if (userId.equals("null")) {
      response.getWriter().print("유저 아이디 오류!");
      response.getWriter().close();
      return;
    }
    if (!(remindAlarmDate.equals(""))) {
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

    PrintWriter out = response.getWriter();
    if (result != 0) {
      response.setStatus(200);
    } else {
      response.setStatus(400);
    }
    out.close();
  }

}
