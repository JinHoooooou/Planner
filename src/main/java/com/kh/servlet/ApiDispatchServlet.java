package com.kh.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet(name = "api-dispatcher", urlPatterns = "/api/*", loadOnStartup = 1)
public class ApiDispatchServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private RequestMapping requestMapping;

  @Override
  public void init() {
    requestMapping = new RequestMapping();
    requestMapping.initMapping();
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String requestUri = req.getRequestURI();
    JSONObject result;
    RestController restController = requestMapping.findController(requestUri);
    try {
      result = restController.execute(req, resp);
    } catch (Throwable e) {
      result = new JSONObject(e.getLocalizedMessage());
    }
    resp.setStatus(result.getInt("status"));
    resp.getWriter().write(result.toString());
    resp.getWriter().close();
  }
}
