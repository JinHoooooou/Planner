package com.kh.server;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

  @Test
  public void httpGetMethodTest() {
    String request = "GET /index.html HTTP/1.1\r\n"
        + "Host: localhost:8080\r\n"
        + "Connection: keep-alive\r\n"
        + "Accept: */*\r\n\r\n";
    InputStream in = new ByteArrayInputStream(request.getBytes());

    HttpRequest httpRequest = new HttpRequest(in);

    assertThat(httpRequest.getMethod()).isEqualTo("GET");
    assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
    assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");
  }

  @Test
  public void httpGetMethodWithQueryStringTest() {
    String request = "GET /index.html?title=test_title&memo=test_memo HTTP/1.1\r\n"
        + "Host: localhost:8080\r\n"
        + "Connection: keep-alive\r\n"
        + "Accept: */*\r\n\r\n";
    InputStream in = new ByteArrayInputStream(request.getBytes());

    HttpRequest httpRequest = new HttpRequest(in);

    assertThat(httpRequest.getMethod()).isEqualTo("GET");
    assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    assertThat(httpRequest.getParameter("title")).isEqualTo("test_title");
    assertThat(httpRequest.getParameter("memo")).isEqualTo("test_memo");
    assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
    assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");
  }

  @Test
  public void httpPostMethodWithRequestBodyTest() {
    String request = "POST /plan/create HTTP/1.1\r\n"
        + "Host: localhost:8080\r\n"
        + "Connection: keep-alive\r\n"
        + "Content-Length: %d\r\n"
        + "Accept: */*\r\n\r\n"
        + "%s";


    String body = "title=test title&memo=test memo";

    InputStream in = new ByteArrayInputStream(
        String.format(request, body.length(), body).getBytes());

    HttpRequest httpRequest = new HttpRequest(in);

    assertThat(httpRequest.getMethod()).isEqualTo("POST");
    assertThat(httpRequest.getPath()).isEqualTo("/plan/create");
    assertThat(httpRequest.getParameter("title")).isEqualTo("test title");
    assertThat(httpRequest.getParameter("memo")).isEqualTo("test memo");
    assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
    assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");
  }
}
