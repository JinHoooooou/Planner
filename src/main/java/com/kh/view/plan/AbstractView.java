package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.INPUT_ERROR;
import static com.kh.view.plan.constant.Constant.INPUT_INDEX;
import static com.kh.view.plan.constant.Constant.INPUT_MENU;

import com.kh.controller.PlanController;
import java.util.Scanner;

public abstract class AbstractView {

  protected int subViewCommand;
  protected PlanController planController;
  protected Scanner scanner;

  public AbstractView() {
    planController = new PlanController();
  }

  public int inputIndex() {
    while (true) {
      System.out.print(INPUT_INDEX);
      int result = Integer.parseInt(scanner.nextLine());
      System.out.println();

      if (result >= 0 && result < planController.size()) {
        return result;
      }
      System.out.println(INPUT_ERROR);
    }
  }

  public void inputSubView() {
    System.out.print(INPUT_MENU);
    subViewCommand = Integer.parseInt(scanner.nextLine());
    System.out.println();
  }

  public abstract void execute();
}
