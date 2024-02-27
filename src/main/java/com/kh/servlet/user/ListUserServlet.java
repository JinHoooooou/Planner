package com.kh.servlet.user;

import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/user/list")
public class ListUserServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("application/json");

    UserDao userDao = new UserDao();
    List<User> list = userDao.findAll();
    JSONArray result = new JSONArray();
    for (User user : list) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("userId", user.getUserId());
      jsonObject.put("userPw", user.getUserPw());
      jsonObject.put("userName", user.getUserName());
      jsonObject.put("nickname", user.getNickname());
      jsonObject.put("phone", user.getPhone());
      jsonObject.put("email", user.getEmail());
      jsonObject.put("enrollDate", user.getEnrollDate());
      result.put(jsonObject);
    }

    resp.getWriter().write(result.toString());
  }
}
