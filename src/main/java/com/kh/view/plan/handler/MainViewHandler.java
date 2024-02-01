package com.kh.view.plan.handler;

import com.kh.controller.PlanController;
import com.kh.view.plan.AbstractView;
import com.kh.view.plan.CreateView;
import com.kh.view.plan.ListPlanView;
import com.kh.view.plan.ModifyTimerView;
import com.kh.view.plan.PlanerAppExitView;
import com.kh.view.plan.SelectPlanView;

public class MainViewHandler {

  private static final int CREATE_PLAN = 1;
  private static final int LIST_ALL_PLAN = 2;
  private static final int SELECT_PLAN = 3;
  private static final int MODIFY_TIMER = 6;
  private static final int EXIT = 9;

  public static AbstractView subView(int menu, PlanController planController) {
    return switch (menu) {
      case CREATE_PLAN -> new CreateView(planController);
      case SELECT_PLAN -> new SelectPlanView(planController);
      case LIST_ALL_PLAN -> new ListPlanView(planController);
      case MODIFY_TIMER -> new ModifyTimerView();
      case EXIT -> new PlanerAppExitView();
      default -> throw new IllegalArgumentException();
    };
  }
}
