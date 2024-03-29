package com.kh.servlet.user;

import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/user/signup")
public class SignUpServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    JSONObject responseBody = new JSONObject();
    try {
      User newUser = User.postRequestDto(req);
      new UserDao().save(newUser);
      resp.setStatus(HttpServletResponse.SC_CREATED);
    } catch (Exception e) {
      responseBody.put("message", e.getLocalizedMessage());
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
    resp.getWriter().write(responseBody.toString());
    resp.getWriter().close();
  }
}