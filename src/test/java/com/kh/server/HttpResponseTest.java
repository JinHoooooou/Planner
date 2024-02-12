package com.kh.server;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class HttpResponseTest {

  private static final String HTML_PATH = "src/main/resources/html";

  @Test
  public void testIndexHtml() throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    HttpResponse response = new HttpResponse(output);

    response.responseHtmlFile("/");
    String[] actual = output.toString().split("\r\n");
    byte[] expected = Files.readAllBytes(Paths.get(HTML_PATH, "index.html"));

    assertThat(actual[0]).isEqualTo("HTTP/1.1 200 OK ");
    assertThat(actual[1]).isEqualTo("Content-Type: text/html;charset=utf-8");
    assertThat(actual[2]).isEqualTo("Content-Length: " + expected.length);

    int index = 4;
    for (String htmlExpected : new String(expected).split("\r\n")) {
      assertThat(actual[index++]).isEqualTo(htmlExpected);
    }
  }
}