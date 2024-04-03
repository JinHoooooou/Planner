package com.kh.servlet.user;

import com.kh.constant.Message;
import com.kh.model.dao.UserDao;
import com.kh.model.dto.SignUpRequestDto;
import com.kh.model.vo.User;
import com.kh.servlet.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class SignUpController implements RestController {

  @Override
  public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
    JSONObject responseBody = new JSONObject();
    try {
      SignUpRequestDto requestDto = SignUpRequestDto.from(request);
      requestDto.validate();

      User newUser = User.from(requestDto);
      new UserDao().save(newUser);

      responseBody.put("message", Message.SUCCESS_SIGNUP);
      responseBody.put("status", HttpServletResponse.SC_CREATED);
    } catch (RuntimeException e) {
      responseBody.put("message", e.getLocalizedMessage());
      responseBody.put("status", HttpServletResponse.SC_BAD_REQUEST);
    }
    return responseBody;
  }
}
