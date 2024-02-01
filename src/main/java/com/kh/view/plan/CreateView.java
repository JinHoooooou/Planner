package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.CREATE_PLAN_HEAD;
import static com.kh.view.plan.constant.Constant.CREATE_PLAN_INPUT_TITLE;
import static com.kh.view.plan.constant.Constant.CREATE_PLAN_RESULT_FAIL;
import static com.kh.view.plan.constant.Constant.CREATE_PLAN_RESULT_SUCCESS_FORMAT;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import java.util.Scanner;

public class CreateView extends AbstractView {

  private String title;
  private Plan newPlan;

  public CreateView(PlanController controller) {
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
