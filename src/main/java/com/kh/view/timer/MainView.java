package com.kh.view.timer;

import static com.kh.view.timer.constant.Constant.MAIN_CREATE;
import static com.kh.view.timer.constant.Constant.MAIN_DELETE;
import static com.kh.view.timer.constant.Constant.MAIN_EXIT;
import static com.kh.view.timer.constant.Constant.MAIN_HEAD;
import static com.kh.view.timer.constant.Constant.MAIN_INPUT_MENU;
import static com.kh.view.timer.constant.Constant.MAIN_READ_ALL;
import static com.kh.view.timer.constant.Constant.MAIN_READ_ONE;
import static com.kh.view.timer.constant.Constant.MAIN_UPDATE;

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
    subView = AbstractView.subView(menu, timerController, scanner);
  }

  public void printMain() {
    System.out.println(MAIN_HEAD);
    System.out.println(MAIN_CREATE);
    System.out.println(MAIN_READ_ONE);
    System.out.println(MAIN_READ_ALL);
    System.out.println(MAIN_UPDATE);
    System.out.println(MAIN_DELETE);
    System.out.println(MAIN_EXIT);
    System.out.print(MAIN_INPUT_MENU);
  }
}
