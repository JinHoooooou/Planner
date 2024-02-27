package com.kh.servlet.user;

import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/user/info")
public class ReadOneUserServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("application/json");

    String userId = req.getParameter("userId");
    UserDao userDao = new UserDao();
    User user = userDao.findByUserId(userId);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("userId", user.getUserId());
    jsonObject.put("userPw", user.getUserPw());
    jsonObject.put("userName", user.getUserName());
    jsonObject.put("nickname", user.getNickname());
    jsonObject.put("email", user.getEmail());
    jsonObject.put("phone", user.getPhone());
    jsonObject.put("enrollDate", user.getEnrollDate());

    resp.getWriter().write(jsonObject.toString());
  }
}
