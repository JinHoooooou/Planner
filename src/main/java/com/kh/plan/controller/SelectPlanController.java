package com.kh.plan.controller;

import com.kh.plan.model.vo.Plan;
import com.kh.plan.service.PlanService;
import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;

public class SelectPlanController extends AbstractController {

  @Override
  public void service(HttpRequest request, HttpResponse response) {
    int planId = Integer.parseInt(request.getParameter("id"));
    Plan target = new PlanService().findById(planId);

    StringBuilder stringBuilder = new StringBuilder();
    detail(stringBuilder, target);

    stringBuilder.append("<br><br>");
    stringBuilder.append("<div>");
    stringBuilder.append("<form action=\"/timer.html\" method=\"get\">");
    stringBuilder.append("<input type=\"submit\" value=\"집중 시작\" />");
    stringBuilder.append("</form>");
    stringBuilder.append("</div>");

    stringBuilder.append("<div>");
    stringBuilder.append("<form action=\"/plan/updateForm\" method=\"get\">");
    stringBuilder.append("<input name=\"id\" type=\"hidden\" value=\"")
        .append(target.getId()).append("\"/>");
    stringBuilder.append("<input type=\"submit\" value=\"Plan 수정\" />");
    stringBuilder.append("</form>");
    stringBuilder.append("</div>");

    stringBuilder.append("<div>");
    stringBuilder.append("<form action=\"/plan/deleteForm\" method=\"get\">");
    stringBuilder.append("<input name=\"id\" type=\"hidden\" value=\"")
        .append(target.getId()).append("\"/>");
    stringBuilder.append("<input type=\"submit\" value=\"Plan 삭제\" />");
    stringBuilder.append("</form>");
    stringBuilder.append("</div>");

    response.responseHtmlString(stringBuilder.toString());
  }
}
