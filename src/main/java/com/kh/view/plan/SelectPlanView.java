package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.INPUT_ERROR;
import static com.kh.view.plan.constant.Constant.LINE;
import static com.kh.view.plan.constant.Constant.SELECTED_PLAN;
import static com.kh.view.plan.constant.Constant.SELECT_BACK_TO_MAIN;
import static com.kh.view.plan.constant.Constant.SELECT_DELETE_PLAN;
import static com.kh.view.plan.constant.Constant.SELECT_PLAN_HEAD;
import static com.kh.view.plan.constant.Constant.SELECT_PLAN_ID;
import static com.kh.view.plan.constant.Constant.SELECT_START_CONCENTRATION;
import static com.kh.view.plan.constant.Constant.SELECT_UPDATE_PLAN;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import com.kh.view.plan.handler.SelectPlanViewHandler;
import java.util.Scanner;

public class SelectPlanView extends AbstractView {

  private Plan target;

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

  public int inputPlanId() {
    while (true) {
      System.out.print(SELECT_PLAN_ID);
      int result = Integer.parseInt(scanner.nextLine());
      System.out.println();

      if (result >= 0) {
        return result;
      }
      System.out.println(INPUT_ERROR);
    }
  }

  private void printMain() {
    System.out.printf(SELECTED_PLAN + LINE, target);
    System.out.println(SELECT_START_CONCENTRATION);
    System.out.println(SELECT_UPDATE_PLAN);
    System.out.println(SELECT_DELETE_PLAN);
    System.out.println(SELECT_BACK_TO_MAIN);
  }

}
