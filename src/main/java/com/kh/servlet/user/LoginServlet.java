package com.kh.servlet.user;

import com.kh.model.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String userId = req.getParameter("userId");
    String userPw = req.getParameter("userPw");

    try {
      new UserDao().login(userId, userPw);
    } catch (IllegalArgumentException e) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
    resp.setStatus(HttpServletResponse.SC_OK);
  }
}
