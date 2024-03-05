package com.kh.servlet.user;

import com.kh.database.DataAccessException;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/user/updateInfo")
public class UpdateUserServlet extends HttpServlet {

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("application/json");
    String userId = String.valueOf(req.getSession().getAttribute("userId"));
    String nickname = req.getParameter("nickname");
    String email = req.getParameter("email");
    String phone = req.getParameter("phone");

    User updateUser = User.builder().
        userId(userId)
        .nickname(nickname)
        .email(email)
        .phone(phone)
        .build();

    JSONObject responseBody = new JSONObject();
    try {
      int result = new UserDao().updateUserInfo(updateUser);
      resp.setStatus(result == 1 ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
      responseBody.put("message", result == 1 ? "수정 성공" : "수정 실패");
    } catch (DataAccessException e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseBody.put("message", "수정 실패");
    }

    resp.getWriter().write(responseBody.toString());
    resp.getWriter().flush();
  }
}
