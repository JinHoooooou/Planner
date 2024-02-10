package com.kh.plan.controller;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

  private static Map<String, Controller> controllers = new HashMap<>();

  static {
    controllers.put("/plan/create", new CreatePlanController());
    controllers.put("/plan/list", new ListPlanController());
    controllers.put("/plan/select", new SelectPlanController());
    controllers.put("/plan/updateForm", new UpdateFormPlanController());
    controllers.put("/plan/update", new UpdatePlanController());
    controllers.put("/plan/deleteForm", new DeleteFormPlanController());
    controllers.put("/plan/delete", new DeletePlanController());
  }

  public static Controller getController(String requestUrl) {
    return controllers.getOrDefault(requestUrl, new HtmlController());
  }
}
