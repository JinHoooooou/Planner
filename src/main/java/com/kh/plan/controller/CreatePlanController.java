package com.kh.plan.controller;

import com.kh.plan.service.PlanService;
import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;
import java.time.LocalDate;

public class CreatePlanController extends AbstractController {

  @Override
  public void service(HttpRequest request, HttpResponse response) {
    new PlanService().create(request.getParameter("title"), LocalDate.now(), LocalDate.now());
    response.redirect("/index.html");
  }
}
