package com.kh.server;

import com.kh.plan.controller.Controller;
import com.kh.plan.controller.RequestMapping;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
  private Socket connection;

  public RequestHandler(Socket connectionSocket) {
    this.connection = connectionSocket;
  }

  public void run() {
    LOGGER.info("New Client: {}, Port: {}", connection.getInetAddress(), connection.getPort());
    try (InputStream in = connection.getInputStream();
        OutputStream out = connection.getOutputStream()) {
      HttpRequest request = new HttpRequest(in);
      HttpResponse response = new HttpResponse(out);

      Controller controller = RequestMapping.getController(request.getPath());
      controller.service(request, response);
    } catch (IOException e) {
      LOGGER.warn(e.getMessage());
    }
  }
}
