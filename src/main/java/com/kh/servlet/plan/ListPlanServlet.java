package com.kh.servlet.plan;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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

@WebServlet("/plan/list")
public class ListPlanServlet extends HttpServlet {


  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    response.setContentType("application/json");
    HttpSession session = request.getSession();
    String userId = (String)session.getAttribute("userId");
//    String titleKeyword = request.getParameter("search");
    
    List<Plan> planList = new PlanDao().findByWriter(userId);
    User loginUser = new UserDao().findByUserId(userId);
    JSONObject responseBody = new JSONObject();
    responseBody.put("nickname", loginUser.getNickname());

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

    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().write(responseBody.toString());
    response.getWriter().flush();
    response.getWriter().close();
  }
}
