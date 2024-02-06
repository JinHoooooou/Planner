package com.kh.view.plan;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
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
