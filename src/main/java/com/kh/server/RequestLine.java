package com.kh.server;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLine {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestLine.class);

  @Getter
  @Setter
  private String method;
  @Getter
  private String path;
  private Map<String, String> params = new HashMap<>();

  public RequestLine(String requestLine) {
    LOGGER.info("Request Line: {}", requestLine);
    String[] tokens = requestLine.split(" ");
    if (tokens.length != 3) {
      throw new IllegalArgumentException(requestLine + "형식에 맞지 않습니다.");
    }
    setMethod(tokens[0]);
    setPath(tokens[1]);
    getParameterFrom(requestLine);
  }

  private void setPath(String token) {
    this.path = getMethod().equals("GET") && token.contains("?")
        ? token.substring(0, token.indexOf("?"))
        : token;
  }

  private void getParameterFrom(String requestLine) {
    if (!requestLine.contains("?")) {
      return;
    }
    String queryString = requestLine.substring(requestLine.indexOf("?") + 1);
    String[] parameterString = queryString.split("&");
    for (String keyValueString : parameterString) {
      String[] keyValue = keyValueString.split("=");
      this.params.put(keyValue[0], keyValue[1]);
    }
  }
}
