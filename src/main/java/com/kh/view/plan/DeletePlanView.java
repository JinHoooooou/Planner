package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.DELETE_PLAN_CANCEL;
import static com.kh.view.plan.constant.Constant.DELETE_PLAN_HEAD;
import static com.kh.view.plan.constant.Constant.DELETE_PLAN_SUCCESS;
import static com.kh.view.plan.constant.Constant.DELETE_PLAN_WARNING_MESSAGE;
import static com.kh.view.plan.constant.Constant.LINE;
import static com.kh.view.plan.constant.Constant.SELECTED_PLAN;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import java.util.Scanner;

public class DeletePlanView extends AbstractView {

  private Plan target;

  public DeletePlanView(PlanController planController, Plan target) {
    this.planController = planController;
    this.target = target;
  }

  @Override
  public void execute() {
    scanner = new Scanner(System.in);
    printMain();
    String yn = scanner.nextLine();
    System.out.println();
    if (yn.equals("Y")) {
      planController.delete(target);
      System.out.println(DELETE_PLAN_SUCCESS);
    } else if (yn.equals("N")) {
      System.out.println(DELETE_PLAN_CANCEL);
    } else {
      throw new IllegalArgumentException("invalid input(" + yn + "), Only Y/N");
    }
  }

  private void printMain() {
    System.out.println(DELETE_PLAN_HEAD);
    System.out.printf(SELECTED_PLAN + LINE, target);
    System.out.println();
    System.out.print(DELETE_PLAN_WARNING_MESSAGE);
  }
}
