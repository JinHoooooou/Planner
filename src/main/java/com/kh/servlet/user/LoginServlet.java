package com.kh.servlet.user;

import com.kh.model.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    String userId = req.getParameter("userId");
    String userPw = req.getParameter("userPw");

    try {
      new UserDao().login(userId, userPw);
      HttpSession session = req.getSession();
      session.setAttribute("userId", userId);
      resp.setStatus(HttpServletResponse.SC_OK);
    } catch (IllegalArgumentException e) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}