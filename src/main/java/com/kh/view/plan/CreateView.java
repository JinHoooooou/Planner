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

  public CreateView(PlanController controller) {
    this.planController = controller;
  }

  @Override
  public void execute() {
    scanner = new Scanner(System.in);
    System.out.println(CREATE_PLAN_HEAD);
    inputTitle();
    Plan result = planController.create(title);
    print(result);
  }

  private void inputTitle() {
    System.out.print(CREATE_PLAN_INPUT_TITLE);
    title = scanner.nextLine();
    System.out.println();
  }

  private void print(Plan result) {
    System.out.println(result != null
        ? String.format(CREATE_PLAN_RESULT_SUCCESS_FORMAT, result)
        : CREATE_PLAN_RESULT_FAIL);
  }
}
