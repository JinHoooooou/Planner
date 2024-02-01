package com.kh.view.plan;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import java.util.Scanner;

public class UpdatePlanTitleView extends AbstractView {

  private final Plan original;

  public UpdatePlanTitleView(PlanController planController, Plan target) {
    this.planController = planController;
    this.original = target;
  }

  @Override
  public void execute() {
    scanner = new Scanner(System.in);
    System.out.print("수정할 제목 입력: ");
    String updateTitle = scanner.nextLine();
    String originalTitle = original.getTitle();
    System.out.println();

    planController.update(original, Plan.create(updateTitle, original.getMemo()));
    print(originalTitle, updateTitle);
  }

  private void print(String from, String to) {
    if (to.isEmpty()) {
      System.out.println("제목 변경 실패");
    } else {
      System.out.println("제목 변경: " + from + "->" + to);
    }
  }
}
