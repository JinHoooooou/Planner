package com.kh.controller.plan;

import com.kh.constant.Message;
import com.kh.controller.RestController;
import com.kh.controller.UserSessionUtils;
import com.kh.model.dao.DetailPlanDao;
import com.kh.model.dao.PlanDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

public class CompletePlanController implements RestController {

  private final PlanDao planDao = new PlanDao();
  private final DetailPlanDao detailDao = new DetailPlanDao();

  @Override
  public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
    JSONObject responseBody = new JSONObject();
    HttpSession session = request.getSession(false);

    if (!UserSessionUtils.isSignIn(session)) {
      responseBody.put("message", Message.INVALID_SESSION);
      responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
      return responseBody;
    }

    try {
      int planId = Integer.parseInt(request.getParameter("planId"));
      String complete = request.getParameter("complete");
      String userId = UserSessionUtils.getUserIdFromSession(session);

      planDao.updateCompleteByPlanIdAndWriter(complete, planId, userId);
      detailDao.updateCompleteByPlanIdAndWriter(complete, planId, userId);

      responseBody.put("status", HttpServletResponse.SC_NO_CONTENT);
    } catch (RuntimeException e) {
      responseBody.put("message", Message.INVALID_REQUEST);
      responseBody.put("status", HttpServletResponse.SC_BAD_REQUEST);
    }

    return responseBody;
  }
}
