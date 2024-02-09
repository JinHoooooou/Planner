package com.kh.server;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class HttpResponseTest {

  private String testDirectory = "src/test/resources/";

  @Test
  public void test() {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    HttpResponse response = new HttpResponse(output);
    byte[] testinput = "hello".getBytes();
    response.response200Header(testinput.length);
    response.responseBody(testinput);
    String testtest = output.toString(StandardCharsets.UTF_8);
    assertThat("").isEqualTo("");
  }
}