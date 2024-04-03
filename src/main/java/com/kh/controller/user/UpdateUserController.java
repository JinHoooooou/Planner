package com.kh.controller.user;

import com.kh.constant.Message;
import com.kh.controller.RestController;
import com.kh.controller.UserSessionUtils;
import com.kh.model.dao.UserDao;
import com.kh.model.dto.UpdateUserRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

public class UpdateUserController implements RestController {

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
      String userId = String.valueOf(session.getAttribute("userId"));

      UpdateUserRequestDto requestDto = UpdateUserRequestDto.from(request);
      new UserDao().updateUserInfo(userId, requestDto);
      session.invalidate();

      responseBody.put("message", Message.SUCCESS_UPDATE_USER);
      responseBody.put("status", HttpServletResponse.SC_OK);
    } catch (RuntimeException e) {
      responseBody.put("message", e.getLocalizedMessage());
      responseBody.put("status", HttpServletResponse.SC_BAD_REQUEST);
    }
    return responseBody;
  }
}
