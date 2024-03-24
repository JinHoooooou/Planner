package com.kh.servlet.detail;

import com.kh.model.dao.DetailPlanDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/detail/*")
public class DetailServlet extends HttpServlet {

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    Object user = session.getAttribute("userId");

    if (user == null) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String[] parts = req.getRequestURI().split("/");
    String detailPlanId = parts[parts.length - 1];
    new DetailPlanDao().deleteByDetailPlanIdAndWriter(
        Integer.parseInt(detailPlanId),
        String.valueOf(user)
    );

    resp.setStatus(HttpServletResponse.SC_OK);
  }
}
