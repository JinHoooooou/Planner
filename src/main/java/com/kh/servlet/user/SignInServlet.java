package com.kh.servlet.user;

import com.kh.model.dao.UserDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user/signin")
public class SignInServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    String userId = req.getParameter("userId");
    String userPw = req.getParameter("userPw");

    try {
      new UserDao().login(userId, userPw);
      HttpSession session = req.getSession();
      session.setAttribute("userId", userId);
      resp.setStatus(HttpServletResponse.SC_OK);
    } catch (IllegalArgumentException e) {
      resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
  }
}
