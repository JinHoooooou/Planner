package com.kh.plan.view.handler;

import com.kh.plan.controller.PlanController;
import com.kh.plan.view.AbstractView;
import com.kh.plan.view.CreatePlanView;
import com.kh.plan.view.ListPlanView;
import com.kh.plan.view.ModifyTimerView;
import com.kh.plan.view.PlanerAppExitView;
import com.kh.plan.view.SelectPlanView;

public class MainViewHandler {

  private static final int CREATE_PLAN = 1;
  private static final int LIST_ALL_PLAN = 2;
  private static final int SELECT_PLAN = 3;
  private static final int MODIFY_TIMER = 4;
  private static final int EXIT = 9;

  public static AbstractView subView(int command, PlanController planController) {
    return switch (command) {
      case CREATE_PLAN -> new CreatePlanView(planController);
      case SELECT_PLAN -> new SelectPlanView(planController);
      case LIST_ALL_PLAN -> new ListPlanView(planController);
      case MODIFY_TIMER -> new ModifyTimerView();
      case EXIT -> new PlanerAppExitView();
      default -> throw new IllegalArgumentException();
    };
  }
}
