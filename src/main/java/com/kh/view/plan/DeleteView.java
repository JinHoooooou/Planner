package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.DELETE_FAIL;
import static com.kh.view.plan.constant.Constant.DELETE_HEAD;
import static com.kh.view.plan.constant.Constant.DELETE_SUCCESS;
import static com.kh.view.plan.constant.Constant.EMPTY;

import com.kh.controller.PlanController;
import java.util.Scanner;

public class DeleteView extends AbstractView {

  public DeleteView(PlanController planController, Scanner scanner) {
    this.planController = planController;
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    System.out.println(DELETE_HEAD);
    if (planController.isEmpty()) {
      System.out.println(EMPTY);
      return;
    }
    int index = inputIndex();
    print(planController.delete(index));
  }

  private void print(boolean result) {
    System.out.println(result ? DELETE_SUCCESS : DELETE_FAIL);
  }
}
