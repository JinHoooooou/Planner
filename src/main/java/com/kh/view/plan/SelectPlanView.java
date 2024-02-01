package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.EMPTY;
import static com.kh.view.plan.constant.Constant.SELECT_PLAN_HEAD;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import com.kh.view.plan.handler.SelectPlanViewHandler;
import java.util.Scanner;

public class SelectPlanView extends AbstractView {

  private Plan target;
  private AbstractView subView;

  public SelectPlanView(PlanController planController) {
    this.subViewCommand = -1;
    this.planController = planController;
  }

  @Override
  public void execute() {
    scanner = new Scanner(System.in);
    System.out.println(SELECT_PLAN_HEAD);
    if (planController.isEmpty()) {
      System.out.println(EMPTY);
      return;
    }
    target = planController.select(inputIndex());
    printMain();
    inputSubView();
    SelectPlanViewHandler.subView(subViewCommand, planController, target).execute();
  }

  private void printMain() {
    System.out.println("선택한 Plan: " + target);
    System.out.println();
    System.out.println("1. 집중 시작");
    System.out.println("2. Plan 수정");
    System.out.println("3. Plan 삭제");
    System.out.println();
  }

}
