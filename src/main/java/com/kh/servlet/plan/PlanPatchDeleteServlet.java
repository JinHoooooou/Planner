package com.kh.servlet.plan;

import com.kh.model.dao.DetailPlanDao;
import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/plan/*")
public class PlanPatchDeleteServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String method = req.getMethod();
    if (method.equals("PATCH")) {
      this.doPatch(req, resp);
    } else {
      super.service(req, resp);
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Object user = req.getSession().getAttribute("userId");
    JSONObject responseBody = new JSONObject();

    try {
      String[] parts = req.getRequestURI().split("/");
      String planId = parts[parts.length - 1];
      BufferedReader reader = req.getReader();
      JSONObject requestBody = new JSONObject(reader.readLine());

      Plan target = new PlanDao().findByPlanIdAndWriter(
          Integer.parseInt(planId), String.valueOf(user));
      target = target.putRequestDto(requestBody);
      new PlanDao().update(target);

      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseBody.put("message", e.getLocalizedMessage());
      resp.getWriter().write(responseBody.toString());
      resp.getWriter().close();
    }
  }

  @Override
  protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Object user = req.getSession().getAttribute("userId");

    try {
      String[] parts = req.getRequestURI().split("/");
      String planId = parts[parts.length - 1];
      BufferedReader reader = req.getReader();
      JSONObject requestBody = new JSONObject(reader.readLine());

      String complete = requestBody.getString("complete");
      new PlanDao().updateCompleteByPlanIdAndWriter(complete, Integer.parseInt(planId), String.valueOf(user));
      new DetailPlanDao().updateCompleteByPlanIdAndWriter(complete, Integer.parseInt(planId), String.valueOf(user));

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
      String planId = parts[parts.length - 1];

      new PlanDao().deleteByPlanIdAndWriter(Integer.parseInt(planId), String.valueOf(user));

      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write(new JSONObject().put("message", e.getLocalizedMessage()).toString());
      resp.getWriter().close();
    }
  }
}
