package com.kh.controller;

import com.kh.model.dao.PlanDao;
import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;
import java.time.LocalDate;

public class CreatePlanController extends AbstractController {

  @Override
  public void service(HttpRequest request, HttpResponse response) {
    new PlanDao().create(request.getParameter("title"), LocalDate.now(), LocalDate.now());
    response.redirect("/index.html");
  }
}
