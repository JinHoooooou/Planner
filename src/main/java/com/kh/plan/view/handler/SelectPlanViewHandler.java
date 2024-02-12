package com.kh.plan.view.handler;

import com.kh.plan.controller.PlanController;
import com.kh.plan.model.vo.Plan;
import com.kh.plan.view.AbstractView;
import com.kh.plan.view.ConcentrateView;
import com.kh.plan.view.DeletePlanView;
import com.kh.plan.view.PlanerAppExitView;
import com.kh.plan.view.UpdatePlanView;

public class SelectPlanViewHandler {

  private static final int START_CONCENTRATION = 1;
  private static final int UPDATE_PLAN = 2;
  private static final int DELETE_PLAN = 3;
  private static final int BACK_MAIN = 9;

  public static AbstractView subView(int menu, PlanController planController, Plan target) {
    return switch (menu) {
      case START_CONCENTRATION -> new ConcentrateView(planController, target);
      case UPDATE_PLAN -> new UpdatePlanView(planController, target);
      case DELETE_PLAN -> new DeletePlanView(planController, target);
      case BACK_MAIN -> new PlanerAppExitView();
      default -> throw new IllegalArgumentException();
    };
  }
}
