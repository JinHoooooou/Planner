package com.kh.controller.user;

import com.kh.constant.Message;
import com.kh.controller.RestController;
import com.kh.controller.UserSessionUtils;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

public class GetUserInfoController implements RestController {

  private UserDao userDao = new UserDao();

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
      String userId = UserSessionUtils.getUserIdFromSession(session);
      User target = userDao.findByUserId(userId);

      responseBody.put("data", target.parseJson());
      responseBody.put("status", HttpServletResponse.SC_OK);
    } catch (RuntimeException e) {
      responseBody.put("message", e.getLocalizedMessage());
      responseBody.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    return responseBody;
  }
}
