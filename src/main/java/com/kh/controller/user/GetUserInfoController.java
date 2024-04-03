package com.kh.controller.user;

import com.kh.constant.Message;
import com.kh.controller.RestController;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

public class GetUserInfoController implements RestController {

  @Override
  public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(false);
    JSONObject responseBody = new JSONObject();

    if (session == null || session.getAttribute("userId") == null) {
      responseBody.put("message", Message.INVALID_SESSION);
      responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
      return responseBody;
    }
    try {
      Object user = session.getAttribute("userId");
      User target = new UserDao().findByUserId(String.valueOf(user));

      responseBody.put("data", target.parseJson());
      responseBody.put("status", HttpServletResponse.SC_OK);
    } catch (RuntimeException e) {
      responseBody.put("message", e.getLocalizedMessage());
      responseBody.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    return responseBody;
  }
}
