package com.kh.controller.user;

import com.kh.controller.RestController;
import com.kh.model.dao.UserDao;
import com.kh.model.dto.SignInRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

public class SignInController implements RestController {

  private UserDao userDao = new UserDao();

  @Override
  public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
    JSONObject responseBody = new JSONObject();

    try {
      SignInRequestDto requestDto = SignInRequestDto.from(request);

      userDao.signIn(requestDto);
      HttpSession session = request.getSession();
      session.setAttribute("userId", requestDto.getUserId());

      responseBody.put("status", HttpServletResponse.SC_NO_CONTENT);
    } catch (RuntimeException e) {
      responseBody.put("status", HttpServletResponse.SC_BAD_REQUEST);
      responseBody.put("message", e.getLocalizedMessage());
    }
    return responseBody;
  }
}
