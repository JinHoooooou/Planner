package com.kh.view.plan.handler;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import com.kh.view.plan.AbstractView;
import com.kh.view.plan.UpdatePlanMemoView;
import com.kh.view.plan.UpdatePlanTitleView;

public class UpdatePlanViewHandler {

  private static final int UPDATE_PLAN_TITLE = 1;
  private static final int UPDATE_PLAN_MEMO = 2;


  public static AbstractView subView(int menu, PlanController planController, Plan target) {
    return switch (menu) {
      case UPDATE_PLAN_TITLE -> new UpdatePlanTitleView(planController, target);
      case UPDATE_PLAN_MEMO -> new UpdatePlanMemoView(planController, target);
      default -> throw new IllegalArgumentException();
    };
  }


}
