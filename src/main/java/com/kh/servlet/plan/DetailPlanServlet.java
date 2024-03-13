package com.kh.servlet.plan;

import com.kh.model.dao.DetailPlanDao;
import com.kh.model.dao.PlanDao;
import com.kh.model.vo.DetailPlan;
import com.kh.model.vo.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/plan")
public class DetailPlanServlet extends HttpServlet {


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("application/json");

    String planId = req.getParameter("planId");
    Plan target = new PlanDao().findByPlanId(Integer.parseInt(planId));
    JSONObject responseBody = new JSONObject();
    JSONObject plan = new JSONObject();
    plan.put("planId", target.getPlanId());
    plan.put("writer", target.getWriter());
    plan.put("title", target.getTitle());
    plan.put("startDate", target.getStartDate());
    plan.put("endDate", target.getEndDate());
    plan.put("remindAlarmDate", target.getRemindAlarmDate());
    plan.put("complete", target.isComplete());

    responseBody.put("plan", plan);

    List<DetailPlan> detailPlanList = new DetailPlanDao()
        .findByWriterAndPlanId(target.getWriter(), target.getPlanId());
    JSONArray detailPlanJsonArray = new JSONArray();

    for (DetailPlan detailPlan : detailPlanList) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("contents", detailPlan.getContents());
      jsonObject.put("detailPlanId", detailPlan.getDetailPlanId());
      jsonObject.put("startTime", detailPlan.getStartTime());
      jsonObject.put("endTime", detailPlan.getEndTime());
      jsonObject.put("remindAlarmTime", detailPlan.getRemindAlarmTime());
      jsonObject.put("complete", detailPlan.isComplete());
      detailPlanJsonArray.put(jsonObject);
    }
    responseBody.put("detailPlanList", detailPlanJsonArray);

    resp.getWriter().write(responseBody.toString());
    resp.getWriter().flush();
  }
}
