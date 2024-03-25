package com.kh.servlet.detail;

import com.kh.model.dao.DetailPlanDao;
import com.kh.model.vo.DetailPlan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet(urlPatterns = "/detail/*")
public class DetailServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    Object user = session.getAttribute("userId");
    JSONObject responseBody = new JSONObject();

    if (user == null) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      responseBody.put("message", "invalid session");
      resp.getWriter().write(responseBody.toString());
      return;
    }

    try {
      DetailPlan newDetail = DetailPlan.dto(req, user);
      newDetail.validate();

      DetailPlan saved = new DetailPlanDao().save(newDetail);
      resp.setStatus(HttpServletResponse.SC_CREATED);
      resp.getWriter().write(parseDetailJsonString(saved));
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseBody.put("message", e.getLocalizedMessage());
      resp.getWriter().write(responseBody.toString());
    }
    resp.getWriter().close();
  }

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


  private String parseDetailJsonString(DetailPlan detail) {
    JSONObject result = new JSONObject();
    result.put("detailPlanId", detail.getDetailPlanId());
    result.put("planId", detail.getPlanId());
    result.put("contents", detail.getContents());
    result.put("startDate", detail.getStartDateString());
    result.put("startTime", detail.getStartTimeString());
    result.put("endTime", detail.getEndTimeString());
    result.put("remindAlarmTime", detail.getRemindAlarmTime());

    return result.toString();
  }
}
