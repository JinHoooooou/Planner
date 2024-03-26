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

@WebServlet("/user/findid")
public class FindUserIdServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    PrintWriter out = resp.getWriter();
    String userName = req.getParameter("userName");
    String phone = req.getParameter("phone");

    User user = new UserDao().findIdByUserName(userName);

    if (user != null && user.getPhone().equals(phone)) {
      resp.setStatus(HttpServletResponse.SC_OK);
      out.println(user.getUserId());
    } else {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }

}
