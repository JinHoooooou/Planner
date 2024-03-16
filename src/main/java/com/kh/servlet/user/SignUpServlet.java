package com.kh.servlet.user;

import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/user/signup")
public class SignUpServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("utf-8");
    resp.setCharacterEncoding("utf-8");
    JSONObject responseBody = new JSONObject();
    try {
      User newUser = User.dto(req);
      newUser.validate();

      new UserDao().save(newUser);
      resp.setStatus(HttpServletResponse.SC_CREATED);
    } catch (ValidationException e) {
      String[] messages = e.getLocalizedMessage().split("\n");
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseBody.put("message", messages);
    }
    resp.getWriter().write(responseBody.toString());
    resp.getWriter().flush();
    resp.getWriter().close();
  }
}
