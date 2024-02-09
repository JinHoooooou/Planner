package com.kh.server;

import com.kh.model.vo.Plan;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);
  private static final String HTML_PATH = "src/main/resources/html";

  private DataOutputStream dataOutputStream;

  public HttpResponse(OutputStream out) {
    this.dataOutputStream = new DataOutputStream(out);
  }

  public void response200Header(int bodyContentLength) {
    try {
      dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
      dataOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
      dataOutputStream.writeBytes("Content-Length: " + bodyContentLength + "\r\n");
      dataOutputStream.writeBytes("\r\n");
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
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

  public void responseBody(byte[] body) {
    try {
      dataOutputStream.write(body, 0, body.length);
      dataOutputStream.writeBytes("\r\n");
      dataOutputStream.flush();
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
  }

  public void responseHtml(String htmlUrl) throws IOException {
    byte[] body = Files.readAllBytes(Paths.get(HTML_PATH, htmlUrl));
    this.response200Header(body.length);
    this.responseBody(body);
  }

  public void responsePlanList(List<Plan> plans) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<table border='1'>");
    for (Plan plan : plans) {
      stringBuilder.append("<tr>");
      stringBuilder.append("<td>").append(plan.getId()).append("</td>");
      stringBuilder.append("<td>").append(plan.getTitle()).append("</td>");
      stringBuilder.append("<td>").append(plan.getMemo()).append("</td>");
      stringBuilder.append("<td>").append(plan.getTimerCount()).append("</td>");
      stringBuilder.append("<td>").append(plan.isClear() ? "완료" : "미완료").append("</td>");
      stringBuilder.append("</tr>");
    }
    stringBuilder.append("</table>");
    byte[] body = stringBuilder.toString().getBytes();
    this.response200Header(body.length);
    this.responseBody(body);
  }

  public void responseOnePlan(Plan plan) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<div>");
    stringBuilder.append("선택한 Plan").append("<br>");
    stringBuilder.append("Title: ").append(plan.getTitle()).append("<br>");
    stringBuilder.append("Memo: ").append(plan.getMemo()).append("<br>");
    stringBuilder.append("Timer Count: ").append(plan.getTimerCount()).append("<br>");
    stringBuilder.append("Status: ").append(plan.isClear() ? "완료" : "미완료").append("<br>");
    stringBuilder.append("</div>");
    stringBuilder.append("<div>");
    stringBuilder.append("<button onclick=\"location.href='/timer.html'\" type=\"button\">");
    stringBuilder.append("집중 시작");
    stringBuilder.append("</button>");
    stringBuilder.append("</div>");
    stringBuilder.append("<div>");
    stringBuilder.append(
        "<button onclick=\"location.href='/updateForm.html'\" type=\"button\">");
    stringBuilder.append("Plan 수정");
    stringBuilder.append("</button>");
    stringBuilder.append("</div>");
    stringBuilder.append("<div>");
    stringBuilder.append("<button onclick=\"location.href='/delete.html'\" type=\"button\">");
    stringBuilder.append("Plan 삭제");
    stringBuilder.append("</button>");
    stringBuilder.append("</div>");
    byte[] body = stringBuilder.toString().getBytes();
    this.response200Header(body.length);
    this.responseBody(body);
  }
}
