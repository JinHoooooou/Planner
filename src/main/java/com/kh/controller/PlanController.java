package com.kh.controller;

import com.kh.model.vo.Plan;
import com.kh.service.PlanService;
import java.util.List;

public class PlanController {

  private PlanService planService;

  public PlanController() {
    planService = new PlanService();
  }


  public Plan create(String title, String memo) {
    return planService.create(title, memo);
  }

  public Plan create(String title) {
    return this.create(title, "");
  }

  public Plan select(int planId) {
    return planService.findById(planId);
  }

  public List<Plan> listAll() {

    return planService.findAll();
  }

  public Plan update(Plan original, String updateTitle, String updateMemo) {

    return planService.update(original, updateTitle, updateMemo);
  }

  public boolean delete(Plan target) {

    return planService.delete(target);
  }

  public void countTimer(Plan target) {
    target.setTimerCount(target.getTimerCount() + 1);
  }
}
