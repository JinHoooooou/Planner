package com.kh.servlet.user;

import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/findpw")
public class FindUserPwServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    PrintWriter out = resp.getWriter();
    String userId = req.getParameter("userId");

    User user = new UserDao().findByUserId(userId);

    if (user != null) {
      resp.setStatus(HttpServletResponse.SC_OK);
      out.println(user.getUserPw());
    } else {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }

}
