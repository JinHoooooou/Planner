package com.kh.servlet.plan;

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
public class PlanPutPatchDeleteServlet extends HttpServlet {

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
}
