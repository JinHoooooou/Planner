package com.kh.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RequestLineTest {

  @Test
  public void httpGetMethodTest() {
    String requestLine = "GET /index.html HTTP/1.1";
    RequestLine line = new RequestLine(requestLine);

    assertThat(line.getMethod()).isEqualTo("GET");
    assertThat(line.getPath()).isEqualTo("/index.html");
  }

  @Test
  public void httpGetMethodWithQueryStringTest() {
    String requestLine = "GET /index.html?title=test_title&memo=test_memo HTTP/1.1";
    RequestLine line = new RequestLine(requestLine);

    assertThat(line.getMethod()).isEqualTo("GET");
    assertThat(line.getPath()).isEqualTo("/index.html");
    assertThat(line.getParam("title")).isEqualTo("test_title");
    assertThat(line.getParam("memo")).isEqualTo("test_memo");
  }

  @Test
  public void httpPostMethod() {
    String requestLine = "POST /plan/create HTTP/1.1";
    RequestLine line = new RequestLine(requestLine);

    assertThat(line.getMethod()).isEqualTo("POST");
    assertThat(line.getPath()).isEqualTo("/plan/create");
  }
}