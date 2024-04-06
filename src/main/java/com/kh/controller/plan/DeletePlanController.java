package com.kh.controller.plan;

import com.kh.controller.RestController;
import com.kh.controller.UserSessionUtils;
import com.kh.model.dao.PlanDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

public class DeletePlanController implements RestController {

  private final PlanDao planDao = new PlanDao();

  @Override
  public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
    JSONObject responseBody = new JSONObject();
    HttpSession session = request.getSession(false);

    try {
      int planId = Integer.parseInt(request.getParameter("planId"));
      String userId = UserSessionUtils.getUserIdFromSession(session);

      new PlanDao().deleteByPlanIdAndWriter(planId, userId);

      responseBody.put("status", HttpServletResponse.SC_NO_CONTENT);
    } catch (RuntimeException e) {
      responseBody.put("message", e.getLocalizedMessage());
      responseBody.put("status", HttpServletResponse.SC_BAD_REQUEST);
    }

    return responseBody;
  }
}
