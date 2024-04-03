package com.kh.servlet.user;

import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/user")
public class UserGetPutDeleteServlet extends HttpServlet {

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession session = req.getSession(false);
    JSONObject responseBody = new JSONObject();
    if (session == null || session.getAttribute("userId") == null) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      responseBody.put("message", "세션이 유효하지 않습니다.");
      resp.getWriter().write(responseBody.toString());
      resp.getWriter().close();
      return;
    }
    Object user = session.getAttribute("userId");

    try {
      User target = new UserDao().findByUserId(String.valueOf(user));
      BufferedReader reader = req.getReader();
      JSONObject requestBody = new JSONObject(reader.readLine());
      target = target.putRequestDto(requestBody);

      new UserDao().updateUserInfo(target);
      session.invalidate();
      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write(new JSONObject().put("message", e.getLocalizedMessage()).toString());
      resp.getWriter().close();
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    JSONObject responseBody = new JSONObject();
    if (session == null || session.getAttribute("userId") == null) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      responseBody.put("message", "세션이 유효하지 않습니다.");
      resp.getWriter().write(responseBody.toString());
      resp.getWriter().close();
      return;
    }
    try {
      new UserDao().deleteByUserId(String.valueOf(session.getAttribute("userId")));

      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write(new JSONObject().put("message", e.getLocalizedMessage()).toString());
      resp.getWriter().close();
    }
  }
}
