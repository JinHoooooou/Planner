package com.kh.controller;

import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;

public class UpdatePlanController extends AbstractController {

  @Override
  public void service(HttpRequest request, HttpResponse response) {
    int id = Integer.parseInt(request.getParameter("id"));
    PlanDao planDao = new PlanDao();
    Plan original = planDao.findById(id);

    planDao.update(original, request.getParameter("title"), request.getParameter("memo"));
    response.redirect("/index.html");
  }
}
