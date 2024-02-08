package com.kh.server;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
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

      byte[] body;
      String path = httpRequest.getPath();
      if (path.equals("/")) {
        body = Files.readAllBytes(Paths.get(HTML_PATH, "index.html"));
        httpResponse.response200Header(body.length);
        httpResponse.responseBody(body);
      } else if (path.equals("/plan/create")) {
        Plan newPlan = planController.create(httpRequest.getParameter("title"));
        LOGGER.info("Plan: {}", newPlan);
        httpResponse.response302CreatePlanSuccessHeader("index.html");
      } else {
        body = Files.readAllBytes(Paths.get(HTML_PATH, path));
        httpResponse.response200Header(body.length);
        httpResponse.responseBody(body);
      }
    } catch (IOException e) {
      LOGGER.warn(e.getMessage());
    }
  }
}
