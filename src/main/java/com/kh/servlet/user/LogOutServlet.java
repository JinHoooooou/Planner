package com.kh.servlet.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/user/logout")
public class LogOutServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession session = req.getSession(false);
    if (session == null || session.getAttribute("userId") == null) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      resp.getWriter().write(new JSONObject().put("message","세션이 유효하지 않습니다.").toString());
      resp.getWriter().close();
      return;
    }

    session.invalidate();
    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }
}
