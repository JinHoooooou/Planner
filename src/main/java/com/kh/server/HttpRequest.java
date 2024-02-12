package com.kh.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);
  @Getter
  private Map<String, String> headers = new HashMap<>();
  @Getter
  private Map<String, String> params = new HashMap<>();
  private RequestLine requestLine;

  public HttpRequest(InputStream in) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    try {
      requestLine = new RequestLine(reader.readLine());
      parseHeaders(reader);
      parseParams(reader);
    } catch (IOException e) {
      LOGGER.warn(e.getMessage());
    }
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

  private void parseParams(BufferedReader bufferedReader) {
    if (getMethod().equals("GET")) {
      this.params = requestLine.getParams();
      return;
    }
    int contentLength = Integer.parseInt(this.headers.get("Content-Length"));
    char[] body = new char[contentLength];
    try {
      bufferedReader.read(body, 0, contentLength);
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
    String requestBodyString = String.valueOf(body);
    String[] parameterString = requestBodyString.split("&");
    for (String keyValueString : parameterString) {
      String[] keyValue = keyValueString.split("=");
      this.params.put(keyValue[0], keyValue[1]);
    }
  }

  public String getHeader(String name) {
    return this.headers.get(name);
  }

  public String getParameter(String key) {
    return this.params.get(key);
  }

  public String getMethod() {
    return requestLine.getMethod();
  }

  public String getPath() {
    return requestLine.getPath();
  }
}
