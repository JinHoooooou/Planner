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
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/detail/list")
public class ListDetailServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String planId = req.getParameter("planId");
    HttpSession session = req.getSession();
    Object writer = session.getAttribute("userId");

    try {
      List<DetailPlan> details =
          new DetailPlanDao().findByWriterAndPlanIdOrderByDetailPlanId(String.valueOf(writer),
              Integer.parseInt(planId));

      JSONObject responseBody = new JSONObject();
      JSONArray jsonArray = new JSONArray();
      for (DetailPlan detail : details) {
        JSONObject detailJson = new JSONObject();
        detailJson.put("detailPlanId", detail.getDetailPlanId());
        detailJson.put("contents", detail.getContents());
        detailJson.put("startDate", detail.getStartDateString());
        detailJson.put("startTime", detail.getStartTimeString());
        detailJson.put("endTime", detail.getEndTimeString());
        detailJson.put("remindAlarmTime", detail.getRemindAlarmTime());
        detailJson.put("complete", detail.getComplete());
        jsonArray.put(detailJson);
      }
      responseBody.put("detailList", jsonArray);

      resp.setStatus(HttpServletResponse.SC_OK);
      resp.getWriter().write(responseBody.toString());
      resp.getWriter().close();
    } catch (NullPointerException | NumberFormatException e) {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}
