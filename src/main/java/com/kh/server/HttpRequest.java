package com.kh.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);
  private Map<String, String> headers = new HashMap<>();
  private Map<String, String> params = new HashMap<>();
  private RequestLine requestLine;

  public HttpRequest(InputStream in) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    try {
      requestLine = new RequestLine(reader.readLine());
      parseHeaders(reader);
    } catch (IOException e) {
      LOGGER.warn(e.getMessage());
    }
  }

  private void setParameters(String requestLine, BufferedReader bufferedReader) {
    String requestBody = "";

    if (getMethod().equals("POST")) {
      requestBody = readBody(bufferedReader);
    } else if (getMethod().equals("GET") && requestLine.contains("?")) {
      requestBody = requestLine.substring(requestLine.indexOf("?") + 1);
    } else {
      return;
    }

    String[] parameterString = requestBody.split("&");
    for (String keyValue : parameterString) {
      String key = keyValue.split("=")[0];
      String value = keyValue.split("=")[1];
      this.params.put(key, value);
    }
  }

  private String readBody(BufferedReader bufferedReader) {

    int contentLength = Integer.parseInt(this.headers.get("Content-Length"));
    char[] body = new char[contentLength];
    try {
      bufferedReader.read(body, 0, contentLength);
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
    return String.valueOf(body);
  }

  private void parseHeaders(BufferedReader bufferedReader) {
    String line;
    try {
      while (!(line = bufferedReader.readLine()).isEmpty()) {
        LOGGER.info("Request Header: {}", line);
        String[] tokens = line.split(":");
        this.headers.put(tokens[0].trim(), tokens[1].trim());
      }
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
  }

  public String getHeader(String name) {
    return this.headers.get(name);
  }

  public String getParameter(String key) {
    return this.params.get(key);
  }
}
