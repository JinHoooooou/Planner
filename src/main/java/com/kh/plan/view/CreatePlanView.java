package com.kh.plan.view;

import static com.kh.plan.view.constant.Constant.CREATE_PLAN_HEAD;
import static com.kh.plan.view.constant.Constant.CREATE_PLAN_INPUT_TITLE;
import static com.kh.plan.view.constant.Constant.CREATE_PLAN_RESULT_FAIL;
import static com.kh.plan.view.constant.Constant.CREATE_PLAN_RESULT_SUCCESS_FORMAT;

import com.kh.plan.controller.PlanController;
import com.kh.plan.model.vo.Plan;
import java.util.Scanner;

public class CreatePlanView extends AbstractView {

  private String title;
  private Plan newPlan;

  public CreatePlanView(PlanController controller) {
    this.planController = controller;
  }

  @Override
  public void execute() {
    scanner = new Scanner(System.in);
    System.out.println(CREATE_PLAN_HEAD);
    inputTitle();
    newPlan = planController.create(title);
    printResult();
  }

  private void inputTitle() {
    System.out.print(CREATE_PLAN_INPUT_TITLE);
    title = scanner.nextLine();
    System.out.println();
  }

  private void printResult() {
    System.out.println(newPlan != null
        ? String.format(CREATE_PLAN_RESULT_SUCCESS_FORMAT, newPlan)
        : CREATE_PLAN_RESULT_FAIL);
  }
}
