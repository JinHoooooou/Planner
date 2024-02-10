package com.kh.plan.view;

import static com.kh.plan.view.constant.Constant.LIST_ALL_PLAN_HEAD;

import com.kh.plan.controller.PlanController;
import com.kh.plan.model.vo.Plan;
import java.util.List;

public class ListPlanView extends AbstractView {

  public ListPlanView(PlanController planController) {
    this.planController = planController;
  }

  @Override
  public void execute() {
    System.out.println(LIST_ALL_PLAN_HEAD);
    print(planController.listAll());
  }

  public void print(List<Plan> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.println("Index " + i + ": " + list.get(i));
    }
  }
}
