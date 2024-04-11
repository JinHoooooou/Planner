package com.kh.controller.detail;

import com.kh.constant.Message;
import com.kh.controller.RestController;
import com.kh.controller.UserSessionUtils;
import com.kh.model.dao.DetailPlanDao;
import com.kh.model.dto.detail.CreateDetailRequestDto;
import com.kh.model.vo.DetailPlan;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

public class CreateDetailController implements RestController {

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
      CreateDetailRequestDto requestDto = CreateDetailRequestDto.from(request);
      requestDto.validate();

      DetailPlan savedDetail = detailDao.save(requestDto, UserSessionUtils.getUserIdFromSession(session));

      responseBody.put("data", savedDetail.parseJson());
      responseBody.put("status", HttpServletResponse.SC_CREATED);
    } catch (RuntimeException e) {
      responseBody.put("message", e.getLocalizedMessage());
      responseBody.put("status", HttpServletResponse.SC_BAD_REQUEST);
    }

    return responseBody;
  }
}
