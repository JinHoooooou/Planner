package com.kh.view.plan;

import com.kh.plan.controller.PlanController;
import com.kh.plan.model.vo.Plan;
import com.kh.plan.view.ConcentrateView;
import org.junit.jupiter.api.Test;

public class ConcentrateViewTest {

  PlanController planController;
  Plan plan;

  @Test
  public void simpleTest1() {

    planController = new PlanController();
    plan = Plan.create("title", "");

    ConcentrateView view = new ConcentrateView(planController, plan);
    view.execute();
  }

}
