package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.EMPTY;
import static com.kh.view.plan.constant.Constant.SELECT_PLAN_HEAD;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import java.util.Scanner;

public class SelectPlanView extends AbstractView {

  public SelectPlanView(PlanController planController) {
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
    Plan plan = planController.selectOne(inputIndex());
    print(plan);
  }

  public void print(Plan plan) {
    System.out.println(plan);
    System.out.println();
  }

}
