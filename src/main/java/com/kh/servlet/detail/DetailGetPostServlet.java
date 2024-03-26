package com.kh.servlet.detail;

import com.kh.model.dao.DetailPlanDao;
import com.kh.model.vo.DetailPlan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(urlPatterns = "/details")
public class DetailGetPostServlet extends HttpServlet {


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Object user = req.getSession().getAttribute("userId");
    JSONObject responseBody = new JSONObject();

    try {
      String planId = req.getParameter("planId");
      List<DetailPlan> details = new DetailPlanDao()
          .findByWriterAndPlanIdOrderByDetailPlanId(String.valueOf(user), Integer.parseInt(planId));

      responseBody.put("detailList", buildJsonArray(details));
      resp.setStatus(HttpServletResponse.SC_OK);
    } catch (NullPointerException | IllegalArgumentException e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
    resp.getWriter().write(responseBody.toString());
    resp.getWriter().close();
  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Object user = req.getSession().getAttribute("userId");
    JSONObject responseBody = new JSONObject();

    try {
      DetailPlan newDetail = DetailPlan.postRequestDto(req, user);

      DetailPlan saved = new DetailPlanDao().save(newDetail);
      resp.setStatus(HttpServletResponse.SC_CREATED);
      responseBody = saved.responseDto();
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseBody.put("message", e.getLocalizedMessage());
    }
    resp.getWriter().write(responseBody.toString());
    resp.getWriter().close();
  }

  private JSONArray buildJsonArray(List<DetailPlan> details) {
    JSONArray result = new JSONArray();
    for (DetailPlan detail : details) {
      result.put(detail.responseDto());
    }
    return result;
  }
}
