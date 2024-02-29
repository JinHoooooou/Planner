package com.kh.controller;

import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;

public class UpdateFormPlanController extends AbstractController {

  @Override
  public void service(HttpRequest request, HttpResponse response) {
    int planId = Integer.parseInt(request.getParameter("id"));
    Plan target = new PlanDao().findById(planId);

    StringBuilder stringBuilder = new StringBuilder();
    detail(stringBuilder, target);

    stringBuilder.append("<div>");
    stringBuilder.append("<form action=\"/plan/update\" method=\"post\">");
    stringBuilder.append("<fieldset>");
    stringBuilder.append("<legend>Plan 수정</legend>");
    stringBuilder.append("Title: ").append("<input name=\"title\" type=\"text\"/>").append("<br>");
    stringBuilder.append("Memo: ").append("<input name=\"memo\" type=\"text\"/>").append("<br>");
    stringBuilder.append("<input name=\"id\" type=\"hidden\" value=\"")
        .append(target.getId()).append("\" />");
    stringBuilder.append("<input type=\"submit\" value=\"수정\" />");
    stringBuilder.append("</fieldset>");
    stringBuilder.append("</form>");
    stringBuilder.append("</div>");

    response.responseHtmlString(stringBuilder.toString());
  }
}
