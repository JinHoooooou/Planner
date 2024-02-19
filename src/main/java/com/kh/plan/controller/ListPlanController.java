package com.kh.plan.controller;

import com.kh.plan.model.vo.Plan;
import com.kh.plan.service.PlanService;
import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;
import java.util.List;

public class ListPlanController extends AbstractController {

  @Override
  public void service(HttpRequest request, HttpResponse response) {
    StringBuilder stringBuilder = new StringBuilder();
    List<Plan> plans = new PlanService().findAll();

    stringBuilder.append("<table border='1'>");
    for (Plan plan : plans) {
      stringBuilder.append("<tr>");

      stringBuilder.append("<td>").append(plan.getId()).append("</td>");
      stringBuilder.append("<td>").append(plan.getTitle()).append("</td>");

      stringBuilder.append("<td>");
      stringBuilder.append("<a href='/plan/select?id=").append(plan.getId()).append("'>");
      stringBuilder.append("상세");
      stringBuilder.append("</a>");
      stringBuilder.append("</td>");

      stringBuilder.append("</tr>");
    }
    stringBuilder.append("</table>");
    response.responseHtmlString(stringBuilder.toString());
  }
}
