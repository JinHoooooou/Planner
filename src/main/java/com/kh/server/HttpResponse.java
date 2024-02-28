package com.kh.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);
  private static final String HTML_PATH = "src/main/resources";

  private DataOutputStream dataOutputStream;

  public HttpResponse(OutputStream out) {
    this.dataOutputStream = new DataOutputStream(out);
  }

  public void redirect(String redirectUrl) {
    try {
      dataOutputStream.writeBytes("HTTP/1.1 302 Redirect \r\n");
      dataOutputStream.writeBytes("Location: " + redirectUrl);
      dataOutputStream.writeBytes("\r\n");
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
  }

  public void responseHtmlFile(String htmlUrl) throws IOException {
    if (htmlUrl.equals("/")) {
      htmlUrl = "/index.html";
    }
    byte[] body = Files.readAllBytes(Paths.get(HTML_PATH, htmlUrl));
    this.response200Header(body.length);
    this.responseBody(body);
  }

  public void responseHtmlString(String htmlString) {
    byte[] body = htmlString.getBytes();
    this.response200Header(body.length);
    this.responseBody(body);
  }

  private void response200Header(int bodyContentLength) {
    try {
      dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
      dataOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
      dataOutputStream.writeBytes("Content-Length: " + bodyContentLength + "\r\n");
      dataOutputStream.writeBytes("\r\n");
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
  }

  private void responseBody(byte[] body) {
    try {
      dataOutputStream.write(body, 0, body.length);
      dataOutputStream.writeBytes("\r\n");
      dataOutputStream.flush();
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
  }
}
