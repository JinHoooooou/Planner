package com.kh.servlet;

import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/user/signup")
public class SignUpServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("utf-8");
    resp.setCharacterEncoding("utf-8");
    try {
      User newUser = User.dto(req);
      newUser.validate();

      new UserDao().save(newUser);
      resp.setStatus(HttpServletResponse.SC_CREATED);
    } catch (Exception e) {
      String message = e.getMessage();
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write(new JSONObject().put("message", message).toString());
    }

    resp.getWriter().flush();
    resp.getWriter().close();
  }
}
