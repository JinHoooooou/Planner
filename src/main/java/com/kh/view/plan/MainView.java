package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.MAIN_CREATE_PLAN;
import static com.kh.view.plan.constant.Constant.MAIN_DELETE_PLAN;
import static com.kh.view.plan.constant.Constant.MAIN_EXIT;
import static com.kh.view.plan.constant.Constant.MAIN_HEAD;
import static com.kh.view.plan.constant.Constant.MAIN_INPUT_MENU;
import static com.kh.view.plan.constant.Constant.MAIN_LIST_PLAN;
import static com.kh.view.plan.constant.Constant.MAIN_MODIFY_TIMER;
import static com.kh.view.plan.constant.Constant.MAIN_SELECT_PLAN;
import static com.kh.view.plan.constant.Constant.MAIN_UPDATE_PLAN;

import java.util.Scanner;

public class MainView extends AbstractView {

  private static final int DEFAULT = -1;
  private int menu;
  private AbstractView subView;

  public MainView() {
    super();
    menu = DEFAULT;
  }

  @Override
  public void execute() {
    scanner = new Scanner(System.in);
    while (menu != 9) {
      printMain();
      inputSubMenu();
      subView.execute();
    }
  }

  private void inputSubMenu() {
    menu = Integer.parseInt(scanner.nextLine());
    System.out.println();
    subView = AbstractView.subView(menu, planController, scanner);
  }

  public void printMain() {
    System.out.println(MAIN_HEAD);
    System.out.println(MAIN_CREATE_PLAN);
    System.out.println(MAIN_LIST_PLAN);
    System.out.println(MAIN_SELECT_PLAN);
    System.out.println(MAIN_UPDATE_PLAN);
    System.out.println(MAIN_DELETE_PLAN);
    System.out.println(MAIN_MODIFY_TIMER);
    System.out.println(MAIN_EXIT);
    System.out.print(MAIN_INPUT_MENU);
  }
}
