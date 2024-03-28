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

@WebServlet("/user/duplicate/userid/*")
public class UserIdDuplicateServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String[] parts = req.getRequestURI().split("/");
    String userId = parts[parts.length - 1];
    JSONObject responseBody = new JSONObject();

    if (userId == null || !userId.matches("^[A-Za-z][A-Za-z0-9_]{7,16}$")) {
      responseBody.put("message", "아이디는 영문자로 시작해야 하며 8~16자의 영문자, 숫자, _를 사용해야합니다.");
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write(responseBody.toString());
      resp.getWriter().close();
      return;
    }

    User user = new UserDao().findByUserId(userId);
    if (user != null) {
      responseBody.put("message", "이미 존재하는 아이디입니다.");
      resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
      resp.getWriter().write(responseBody.toString());
      resp.getWriter().close();
      return;
    }

    responseBody.put("message", "사용 가능한 아이디입니다.");
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.getWriter().write(responseBody.toString());
    resp.getWriter().close();
  }
}
