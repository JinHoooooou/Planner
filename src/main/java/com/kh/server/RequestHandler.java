package com.kh.server;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
  private static final String HTML_PATH = "src/main/resources/html";
  private Socket connection;
  private PlanController planController;

  public RequestHandler(Socket connectionSocket) {
    this.connection = connectionSocket;
    this.planController = new PlanController();

  }

  public void run() {
    LOGGER.info("New Client: {}, Port: {}", connection.getInetAddress(), connection.getPort());
    try (InputStream in = connection.getInputStream();
        OutputStream out = connection.getOutputStream()) {
      HttpRequest httpRequest = new HttpRequest(in);
      HttpResponse httpResponse = new HttpResponse(out);

      String path = httpRequest.getPath();
      if (path.equals("/")) {
        httpResponse.responseHtml("/index.html");
      } else if (path.equals("/plan/create")) {
        planController.create(httpRequest.getParameter("title"));
        httpResponse.redirect("/index.html");
      } else if (path.equals("/list")) {
        List<Plan> plans = planController.listAll();
        httpResponse.responsePlanList(plans);
      } else if (path.equals("/select")) {
        int planId = Integer.parseInt(httpRequest.getParameter("id"));
        Plan plan = planController.select(planId);
        httpResponse.responseOnePlan(plan);
      } else {
        httpResponse.responseHtml(path);
      }
    } catch (IOException e) {
      LOGGER.warn(e.getMessage());
    }
  }
}
