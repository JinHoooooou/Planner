package com.kh.controller.user;

import com.kh.constant.Message;
import com.kh.constant.Regex;
import com.kh.controller.RestController;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class UserIdDuplicateController implements RestController {


  @Override
  public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
    String userId = request.getParameter("userId");
    JSONObject responseBody = new JSONObject();

    if (userId == null || !userId.matches(Regex.USER_ID)) {
      responseBody.put("message", Message.ERROR_USER_ID);
      responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
      return responseBody;
    }

    User user = new UserDao().findByUserId(userId);
    if (user != null) {
      responseBody.put("message", Message.DUPLICATE_USER_ID);
      responseBody.put("status", HttpServletResponse.SC_FORBIDDEN);
      return responseBody;
    }

    responseBody.put("message", Message.SUCCESS_USER_ID);
    responseBody.put("status", HttpServletResponse.SC_OK);
    return responseBody;
  }
}
