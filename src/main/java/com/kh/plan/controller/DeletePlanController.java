package com.kh.plan.controller;

import com.kh.plan.model.vo.Plan;
import com.kh.plan.service.PlanService;
import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;

public class DeletePlanController implements Controller {

  @Override
  public void service(HttpRequest request, HttpResponse response) {
    int id = Integer.parseInt(request.getParameter("id"));
    PlanService planService = new PlanService();
    Plan target = planService.findById(id);

    planService.delete(target);
    response.redirect("/index.html");
  }
}
