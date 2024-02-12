package com.kh.plan.view;

import static com.kh.plan.view.constant.Constant.INPUT_MENU;

import com.kh.plan.controller.PlanController;
import java.util.Scanner;

public abstract class AbstractView {

  protected int subViewCommand;
  protected PlanController planController;
  protected Scanner scanner;

  public AbstractView() {
    planController = new PlanController();
  }

  public void inputSubView() {
    System.out.print(INPUT_MENU);
    subViewCommand = Integer.parseInt(scanner.nextLine());
    System.out.println();
  }

  public abstract void execute();
}
