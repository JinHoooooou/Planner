package com.kh.servlet.user;

import com.kh.constant.Message;
import com.kh.model.dao.UserDao;
import com.kh.model.dto.UpdateUserRequestDto;
import com.kh.servlet.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

public class UpdateUserController implements RestController {

  @Override
  public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(false);
    JSONObject responseBody = new JSONObject();
    if (session == null || session.getAttribute("userId") == null) {
      responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
      responseBody.put("message", Message.INVALID_SESSION);
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
