package com.kh.servlet.user;

import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

@WebServlet("/user/duplicate/nickname/*")
public class NicknameDuplicateServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String[] parts = URLDecoder.decode(req.getRequestURI(), StandardCharsets.UTF_8).split("/");
    String nickname = parts[parts.length - 1];
    JSONObject responseBody = new JSONObject();

    if (nickname == null || !nickname.matches("^[가-힣a-zA-Z0-9]{3,20}$")) {
      responseBody.put("message", "닉네임은 3~20자의 한글, 영문, 숫자를 사용해야합니다.");
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write(responseBody.toString());
      resp.getWriter().close();
      return;
    }

    User user = new UserDao().findByNickname(nickname);
    if (user != null) {
      responseBody.put("message", "이미 존재하는 닉네임입니다.");
      resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
      resp.getWriter().write(responseBody.toString());
      resp.getWriter().close();
      return;
    }
    responseBody.put("message", "사용 가능한 닉네임입니다.");
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.getWriter().write(responseBody.toString());
    resp.getWriter().close();
  }
}
