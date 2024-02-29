package com.kh.controller;

import com.kh.model.vo.Plan;
import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;

public abstract class AbstractController implements Controller {

  public abstract void service(HttpRequest request, HttpResponse response);

  public void detail(StringBuilder stringBuilder, Plan target) {
    stringBuilder.append("<div style=\"border:1px solid\">");
    stringBuilder.append("선택한 Plan").append("<br>");
    stringBuilder.append("Title: ").append(target.getTitle()).append("<br>");
    stringBuilder.append("</div>");
  }
}
