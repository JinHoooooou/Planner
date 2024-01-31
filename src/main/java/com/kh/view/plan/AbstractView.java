package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.INPUT_ERROR;
import static com.kh.view.plan.constant.Constant.INPUT_INDEX;

import com.kh.controller.PlanController;
import java.util.Scanner;

public abstract class AbstractView {

  private static final int CREATE_PLAN = 1;
  private static final int LIST_ALL_PLAN = 2;
  private static final int SELECT_PLAN = 3;
  private static final int UPDATE_PLAN = 4;
  private static final int DELETE_PLAN = 5;
  private static final int MODIFY_TIMER = 6;
  private static final int EXIT = 9;
  protected PlanController planController;
  protected Scanner scanner;

  public AbstractView() {
    planController = new PlanController();
  }

  public static AbstractView subView(int menu, PlanController planController, Scanner scanner) {
    return switch (menu) {
      case CREATE_PLAN -> new CreateView(planController);
      case SELECT_PLAN -> new SelectPlanView(planController);
      case LIST_ALL_PLAN -> new ListPlanView(planController);
      case UPDATE_PLAN -> new UpdateView(planController);
      case DELETE_PLAN -> new DeleteView(planController, scanner);
      case EXIT -> new TimerAppExitView();
      default -> throw new IllegalArgumentException();
    };
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

  public abstract void execute();
}
