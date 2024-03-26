package com.kh.servlet.detail;

import com.kh.model.dao.DetailPlanDao;
import com.kh.model.vo.DetailPlan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet(urlPatterns = "/detail/*")
public class DetailPutPatchDeleteServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String method = req.getMethod();
    if (!method.equals("PATCH")) {
      super.service(req, resp);
    } else {
      this.doPatch(req, resp);
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Object user = req.getSession().getAttribute("userId");

    try {
      String[] parts = req.getRequestURI().split("/");
      String detailPlanId = parts[parts.length - 1];
      BufferedReader reader = req.getReader();
      JSONObject requestBody = new JSONObject(reader.readLine());

      DetailPlan target = new DetailPlanDao()
          .findByDetailPlanIdAndWriter(Integer.parseInt(detailPlanId), String.valueOf(user));
      target = target.putRequestDto(requestBody);
      new DetailPlanDao().update(target);

      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write(new JSONObject().put("message", e.getLocalizedMessage()).toString());
      resp.getWriter().close();
    }
  }

  @Override
  protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Object user = req.getSession().getAttribute("userId");

    try {
      String[] parts = req.getRequestURI().split("/");
      String detailPlanId = parts[parts.length - 1];
      BufferedReader reader = req.getReader();
      JSONObject requestBody = new JSONObject(reader.readLine());

      String complete = requestBody.getString("complete");
      new DetailPlanDao()
          .updateCompleteByDetailPlanIdAndWriter(complete, Integer.parseInt(detailPlanId), String.valueOf(user));

      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write(new JSONObject().put("message", e.getLocalizedMessage()).toString());
      resp.getWriter().close();
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Object user = req.getSession().getAttribute("userId");

    try {
      String[] parts = req.getRequestURI().split("/");
      String detailPlanId = parts[parts.length - 1];

      new DetailPlanDao()
          .deleteByDetailPlanIdAndWriter(Integer.parseInt(detailPlanId), String.valueOf(user));

      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write(new JSONObject().put("message", e.getLocalizedMessage()).toString());
      resp.getWriter().close();
    }
  }
}
