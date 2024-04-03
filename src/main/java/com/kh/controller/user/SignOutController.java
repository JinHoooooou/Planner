package com.kh.controller.user;

import com.kh.constant.Message;
import com.kh.controller.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

public class SignOutController implements RestController {

  @Override
  public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(false);
    JSONObject responseBody = new JSONObject();
    if (session == null || session.getAttribute("userId") == null) {
      responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
      responseBody.put("message", Message.INVALID_SESSION);
      return responseBody;
    }

    session.invalidate();
    responseBody.put("status", HttpServletResponse.SC_NO_CONTENT);
    return responseBody;
  }
}
