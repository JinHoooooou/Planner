package com.kh.controller;

import com.kh.model.vo.Plan;
import java.util.ArrayList;
import java.util.List;

public class PlanController {

  List<Plan> planList = new ArrayList<>();

  public Plan create(String title, String memo) {
    if (title.isEmpty()) {
      return null;
    }
    Plan newPlan = Plan.create(title, memo);
    planList.add(newPlan);
    return newPlan;
  }

  public Plan create(String title) {
    return this.create(title, "");
  }

  public Plan select(int index) {
    return planList.get(index);
  }

  public List<Plan> listAll() {
    return planList;
  }

  public Plan update(Plan original, Plan toUpdate) {
    if (toUpdate.getTitle().isEmpty()) {
      throw new IllegalArgumentException("invalid title");
    }
    return original.update(toUpdate);
  }

  public int size() {
    return planList.size();
  }

  public boolean isEmpty() {
    return planList.isEmpty();
  }

  public boolean delete(int index) {
    planList.remove(index);
    return true;
  }

  public boolean delete(Plan target) {
    if (target == null) {
      throw new IllegalArgumentException("null object");
    }
    return planList.remove(target);
  }

  public void countTimer(Plan target) {
    target.setTimerCount(target.getTimerCount() + 1);
  }
}
