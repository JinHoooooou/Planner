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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONObject;

@WebServlet("/detail/create")
public class DetailPlanCreateServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    session.setAttribute("userId", "validUserId1");

    if (session == null || session.getAttribute("userId") == null) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String planId = req.getParameter("planId");
    String writer = String.valueOf(session.getAttribute("userId"));
    String content = req.getParameter("contents");
    String startDate = req.getParameter("startDate");
    String startTime = req.getParameter("startTime");
    String endTime = req.getParameter("endTime");
    String remindAlarmTime = req.getParameter("remindAlarmTime");

    DetailPlan newDetail = DetailPlan.builder()
        .planId(Integer.parseInt(planId))
        .writer(writer)
        .contents(content)
        .startTime(LocalDateTime.parse(startDate + startTime, DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm")))
        .endTime(LocalDateTime.parse(startDate + endTime, DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm")))
        .remindAlarmTime(LocalDateTime.parse(remindAlarmTime))
        .build();

    DetailPlan savedPlan = new DetailPlanDao().save(newDetail);

    JSONObject responseBody = new JSONObject();
    responseBody.put("detailPlanId", savedPlan.getDetailPlanId());
    responseBody.put("planId", savedPlan.getPlanId());
    responseBody.put("contents", savedPlan.getContents());
    responseBody.put("startTime", savedPlan.getStartTime());
    responseBody.put("endTime", savedPlan.getEndTime());
    responseBody.put("remindAlarmTime", savedPlan.getRemindAlarmTime());

    resp.setStatus(HttpServletResponse.SC_CREATED);
    resp.getWriter().write(responseBody.toString());
    resp.getWriter().flush();
    resp.getWriter().close();
  }
}
