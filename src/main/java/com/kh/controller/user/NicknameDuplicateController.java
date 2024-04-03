package com.kh.controller.user;

import com.kh.constant.Message;
import com.kh.constant.Regex;
import com.kh.controller.RestController;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class NicknameDuplicateController implements RestController {

  @Override
  public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
    String nickname = request.getParameter("nickname");
    JSONObject responseBody = new JSONObject();

    if (nickname == null || !nickname.matches(Regex.USER_NICKNAME)) {
      responseBody.put("message", Message.ERROR_USER_NICKNAME);
      responseBody.put("status", HttpServletResponse.SC_BAD_REQUEST);
      return responseBody;
    }

    User user = new UserDao().findByNickname(nickname);
    if (user != null) {
      responseBody.put("message", Message.DUPLICATE_USER_NICKNAME);
      responseBody.put("status", HttpServletResponse.SC_FORBIDDEN);
      return responseBody;
    }
    responseBody.put("message", Message.SUCCESS_USER_NICKNAME);
    responseBody.put("status", HttpServletResponse.SC_OK);
    return responseBody;
  }
}
