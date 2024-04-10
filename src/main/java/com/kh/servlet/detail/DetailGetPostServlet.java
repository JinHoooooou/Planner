package com.kh.servlet.detail;

import com.kh.model.dao.DetailPlanDao;
import com.kh.model.vo.DetailPlan;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet(urlPatterns = "/details")
public class DetailGetPostServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Object user = req.getSession().getAttribute("userId");
    JSONObject responseBody = new JSONObject();

    try {
      DetailPlan newDetail = DetailPlan.postRequestDto(req, user);

      DetailPlan saved = new DetailPlanDao().save(newDetail);
      resp.setStatus(HttpServletResponse.SC_CREATED);
      responseBody = saved.parseJson();
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseBody.put("message", e.getLocalizedMessage());
    }
    resp.getWriter().write(responseBody.toString());
    resp.getWriter().close();
  }
}
