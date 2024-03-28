package com.kh.servlet.user;

import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/user/find/id")
public class FindUserIdServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String userName = req.getParameter("userName");
    String phone = req.getParameter("phone");

    User target = new UserDao().findByUserNameAndPhone(userName, phone);
    JSONObject responseBody = new JSONObject();

    if (target == null) {
      responseBody.put("message", "등록 되지 않은 회원입니다.");
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    } else {
      responseBody.put("message", String.format("%s님의 아이디는 %s입니다.", userName, target.getUserId()));
      resp.setStatus(HttpServletResponse.SC_OK);
    }
    resp.getWriter().write(responseBody.toString());
    resp.getWriter().close();
  }
}
