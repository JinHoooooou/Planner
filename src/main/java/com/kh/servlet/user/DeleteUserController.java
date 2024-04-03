package com.kh.servlet.user;

import com.kh.constant.Message;
import com.kh.model.dao.UserDao;
import com.kh.servlet.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

public class DeleteUserController implements RestController {

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
      new UserDao().deleteByUserId(String.valueOf(session.getAttribute("userId")));

      responseBody.put("status", HttpServletResponse.SC_NO_CONTENT);
    } catch (RuntimeException e) {
      responseBody.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      responseBody.put("message", e.getLocalizedMessage());
    }

    return responseBody;
  }
}
