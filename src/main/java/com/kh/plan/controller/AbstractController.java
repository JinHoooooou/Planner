package com.kh.plan.controller;

import com.kh.plan.model.vo.Plan;
import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;

public abstract class AbstractController implements Controller {

  public abstract void service(HttpRequest request, HttpResponse response);

  public void detail(StringBuilder stringBuilder, Plan target) {
    stringBuilder.append("<div style=\"border:1px solid\">");
    stringBuilder.append("선택한 Plan").append("<br>");
    stringBuilder.append("Title: ").append(target.getTitle()).append("<br>");
    stringBuilder.append("Memo: ").append(target.getMemo()).append("<br>");
    stringBuilder.append("Timer Count: ").append(target.getTimerCount()).append("<br>");
    stringBuilder.append("Status: ").append(target.isClear() ? "완료" : "미완료").append("<br>");
    stringBuilder.append("</div>");
  }
}
