package com.kh.servlet.plan;

import com.kh.model.dao.PlanDao;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.Plan;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/plans")
public class PlanGetPostServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Object user = req.getSession().getAttribute("userId");
    JSONObject responseBody = new JSONObject();

    try {
      String nickname = new UserDao().findByUserId(String.valueOf(user)).getNickname();
      List<Plan> plans = new PlanDao().findByWriterOrderByEndDate(String.valueOf(user));
      responseBody.put("nickname", nickname);
      responseBody.put("planList", buildJsonArray(plans));

      resp.setStatus(HttpServletResponse.SC_OK);
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
    resp.getWriter().write(responseBody.toString());
    resp.getWriter().close();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Object user = req.getSession().getAttribute("userId");
    JSONObject responseBody = new JSONObject();

    try {
      Plan newPlan = Plan.postRequestDto(req, user);
      new PlanDao().save(newPlan);
      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseBody.put("message", e.getLocalizedMessage());
    }
    resp.getWriter().write(responseBody.toString());
    resp.getWriter().close();
  }

  private JSONArray buildJsonArray(List<Plan> plans) {
    JSONArray result = new JSONArray();
    for (Plan plan : plans) {
      result.put(plan.responseDto());
    }
    return result;
  }
}
