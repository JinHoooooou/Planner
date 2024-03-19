package com.kh.servlet.plan;

import com.kh.model.dao.PlanDao;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.Plan;
import com.kh.model.vo.User;
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
public class PlanServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    Object writer = session.getAttribute("userId");

    if (writer == null) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    User loginUser = new UserDao().findByUserId(String.valueOf(writer));
    if (loginUser == null) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    JSONObject responseBody = new JSONObject();
    responseBody.put("writer", loginUser.getNickname());

    List<Plan> planList = new PlanDao().findByWriter(loginUser.getUserId());
    JSONArray jsonArray = new JSONArray();
    for (Plan plan : planList) {
      JSONObject planJson = new JSONObject();
      planJson.put("planId", plan.getPlanId());
      planJson.put("title", plan.getTitle());
      planJson.put("startDate", plan.getStartDate());
      planJson.put("endDate", plan.getEndDate());
      planJson.put("remindAlarmDate", plan.getRemindAlarmDate());
      planJson.put("complete", plan.getComplete());
      jsonArray.put(planJson);
    }
    responseBody.put("planList", jsonArray);

    resp.setStatus(HttpServletResponse.SC_OK);
    resp.getWriter().write(responseBody.toString());
    resp.getWriter().flush();
    resp.getWriter().close();
  }
}
