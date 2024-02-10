package com.kh.plan.controller;

import com.kh.plan.model.vo.Plan;
import com.kh.plan.service.PlanService;
import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;

public class UpdatePlanController implements Controller {

  @Override
  public void service(HttpRequest request, HttpResponse response) {
    int id = Integer.parseInt(request.getParameter("id"));
    PlanService planService = new PlanService();
    Plan original = planService.findById(id);

    planService.update(original, request.getParameter("title"), request.getParameter("memo"));
    response.redirect("/index.html");
  }
}
