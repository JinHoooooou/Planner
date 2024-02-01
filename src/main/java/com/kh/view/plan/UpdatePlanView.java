package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.UPDATE_PLAN_HEAD;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import com.kh.view.plan.handler.UpdatePlanViewHandler;
import java.util.Scanner;

public class UpdatePlanView extends AbstractView {

  private Plan original;
  private AbstractView updatePlanSubView;

  public UpdatePlanView(PlanController planController, Plan original) {
    this.subViewCommand = -1;
    this.planController = planController;
    this.original = original;
  }

  @Override
  public void execute() {
    scanner = new Scanner(System.in);
    printMain();
    inputUpdateTitle();
    inputUpdateMemo();
    inputSubView();
    updatePlanSubView = UpdatePlanViewHandler.subView(subViewCommand, planController, original);
    updatePlanSubView.execute();
  }

  private void printMain() {
    System.out.println(UPDATE_PLAN_HEAD);
    System.out.println("선택한 Plan: " + original);
    System.out.println();
    System.out.println("1. 제목 수정");
    System.out.println("2. 메모 수정");
  }

}
