package com.kh.view.plan.handler;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import com.kh.view.plan.AbstractView;
import com.kh.view.plan.DeleteView;
import com.kh.view.plan.PlanerAppExitView;
import com.kh.view.plan.UpdatePlanView;

public class SelectPlanViewHandler {

  private static final int START_CONCENTRATION = 1;
  private static final int UPDATE_PLAN = 2;
  private static final int DELETE_PLAN = 3;

  public static AbstractView subView(int menu, PlanController planController, Plan target) {
    return switch (menu) {
      case START_CONCENTRATION -> new PlanerAppExitView();
      case UPDATE_PLAN -> new UpdatePlanView(planController, target);
      case DELETE_PLAN -> new DeleteView(planController);
      default -> throw new IllegalArgumentException();
    };
  }
}
