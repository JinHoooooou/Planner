package com.kh.plan.controller;

import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlController extends AbstractController {

  private static final Logger LOGGER = LoggerFactory.getLogger(HtmlController.class);

  @Override
  public void service(HttpRequest request, HttpResponse response) {
    try {
      response.responseHtmlFile(request.getPath());
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
  }
}
