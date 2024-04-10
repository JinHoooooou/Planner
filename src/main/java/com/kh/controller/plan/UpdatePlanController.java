package com.kh.controller.plan;

import com.kh.constant.Message;
import com.kh.controller.RestController;
import com.kh.controller.UserSessionUtils;
import com.kh.model.dao.PlanDao;
import com.kh.model.dto.UpdatePlanRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

public class UpdatePlanController implements RestController {

  private final PlanDao planDao = new PlanDao();

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
      UpdatePlanRequestDto requestDto = UpdatePlanRequestDto.from(request);
      requestDto.validate();

      planDao.update(planId, requestDto);

      responseBody.put("status", HttpServletResponse.SC_NO_CONTENT);
    } catch (RuntimeException e) {
      responseBody.put("message", e.getLocalizedMessage());
      responseBody.put("status", HttpServletResponse.SC_BAD_REQUEST);
    }

    return responseBody;
  }
}
