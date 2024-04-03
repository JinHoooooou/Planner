package com.kh.servlet.user;

import com.kh.model.dao.UserDao;
import com.kh.model.dto.SignInRequestDto;
import com.kh.servlet.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

public class SignInController implements RestController {

  @Override
  public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
    JSONObject responseBody = new JSONObject();

    try {
      SignInRequestDto requestDto = SignInRequestDto.from(request);

      new UserDao().signIn(requestDto);
      HttpSession session = request.getSession();
      session.setAttribute("userId", requestDto.getUserId());

      responseBody.put("status", HttpServletResponse.SC_NO_CONTENT);
      responseBody.put("message", "");
    } catch (RuntimeException e) {
      responseBody.put("status", HttpServletResponse.SC_BAD_REQUEST);
      responseBody.put("message", e.getLocalizedMessage());
    }
    return responseBody;
  }
}
