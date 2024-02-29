package com.kh.controller;

import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;

public class DeletePlanController extends AbstractController {

  @Override
  public void service(HttpRequest request, HttpResponse response) {
    int id = Integer.parseInt(request.getParameter("id"));
    PlanDao planDao = new PlanDao();
    Plan target = planDao.findById(id);

    planDao.delete(target);
    response.redirect("/index.html");
  }
}
