package com.kh.plan.controller;

import com.kh.plan.service.PlanService;
import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;

public class CreatePlanController implements Controller {

  @Override
  public void service(HttpRequest request, HttpResponse response) {
    new PlanService().create(request.getParameter("title"), "");
    response.redirect("/index.html");
  }
}
