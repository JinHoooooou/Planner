package com.kh.controller.user;

import com.kh.constant.Message;
import com.kh.controller.RestController;
import com.kh.controller.UserSessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

public class SignOutController implements RestController {

  @Override
  public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
    JSONObject responseBody = new JSONObject();
    HttpSession session = request.getSession(false);

    if (!UserSessionUtils.isSignIn(session)) {
      responseBody.put("message", Message.INVALID_SESSION);
      responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
      return responseBody;
    }

    session.invalidate();
    responseBody.put("status", HttpServletResponse.SC_NO_CONTENT);
    return responseBody;
  }
}
