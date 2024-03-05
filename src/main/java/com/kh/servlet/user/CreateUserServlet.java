package com.kh.servlet.user;

import com.kh.database.DataAccessException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/user/create")
public class CreateUserServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("application/json");
    JSONObject responseBody = new JSONObject();
    if (true) { //!User.isValid(req)) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseBody.put("message", "잘못된 요청");
      resp.getWriter().write(responseBody.toString());
      resp.getWriter().flush();
      return;
    }
//    User newUser = User.createFrom(req);
    try {
      resp.setStatus(HttpServletResponse.SC_CREATED);
    } catch (DataAccessException e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }
}
