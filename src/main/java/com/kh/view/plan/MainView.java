package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.MAIN_CREATE_PLAN;
import static com.kh.view.plan.constant.Constant.MAIN_DELETE_PLAN;
import static com.kh.view.plan.constant.Constant.MAIN_EXIT;
import static com.kh.view.plan.constant.Constant.MAIN_HEAD;
import static com.kh.view.plan.constant.Constant.MAIN_LIST_PLAN;
import static com.kh.view.plan.constant.Constant.MAIN_MODIFY_TIMER;
import static com.kh.view.plan.constant.Constant.MAIN_SELECT_PLAN;
import static com.kh.view.plan.constant.Constant.MAIN_UPDATE_PLAN;

import com.kh.view.plan.handler.MainViewHandler;
import java.util.Scanner;

public class MainView extends AbstractView {

  private static final int DEFAULT = -1;
  private AbstractView mainSubView;

  public MainView() {
    super();
    subViewCommand = DEFAULT;
  }

  @Override
  public void execute() {
    scanner = new Scanner(System.in);
    while (subViewCommand != 9) {
      printMain();
      inputSubView();
      MainViewHandler.subView(subViewCommand, planController).execute();
    }
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
  }
}
