package com.kh.servlet;

import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/plan/list")
public class ListPlanServlet extends HttpServlet {


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("application/json");
    HttpSession session = req.getSession();
    session.setAttribute("userId", "validUserId1");

    if (session == null || session.getAttribute("userId") == null) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    List<Plan> plans = new PlanDao().findByWriterOrderByEndDate("validUserId1");
    JSONObject responseBody = new JSONObject();
    JSONArray jsonArrayPlan = new JSONArray();
    for (Plan plan : plans) {
      JSONObject jsonPlan = new JSONObject();
      jsonPlan.put("planId", plan.getPlanId());
      jsonPlan.put("title", plan.getTitle());
      jsonPlan.put("startDate", plan.getStartDate());
      jsonPlan.put("endDate", plan.getEndDate());
      jsonPlan.put("remindAlarmDate", plan.getRemindAlarmDate());
      jsonPlan.put("complete", plan.isComplete());
      jsonArrayPlan.put(jsonPlan);
    }
    responseBody.put("planList", jsonArrayPlan);
    resp.getWriter().write(responseBody.toString());

    resp.getWriter().flush();
    resp.getWriter().close();
  }

}
