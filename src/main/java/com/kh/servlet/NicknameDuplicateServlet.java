package com.kh.servlet;

import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/signup/nickname/duplicate")
public class NicknameDuplicateServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String nickname = req.getParameter("nickname");
    if (nickname == null) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    User user = new UserDao().findByNickname(nickname);
    if (user != null) {
      resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
      return;
    }

    resp.setStatus(HttpServletResponse.SC_OK);
  }
}
