package com.kh.controller;

import com.kh.model.vo.Plan;
import java.util.ArrayList;
import java.util.List;

public class PlanController {

  private static final int DEFAULT_HOUR = 0;
  private static final int DEFAULT_MINUTE = 20;
  private static final int DEFAULT_SECOND = 0;
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


  public boolean create(String title, int hour, int minute, int second) {
    if (!isValid(title, hour, minute, second)) {
      return false;
    }
    Plan newPlan = Plan.create(title, hour, minute, second);
    planList.add(newPlan);

    return true;
  }

  private static boolean isValid(String title, int hour, int minute, int second) {
    if (title.isEmpty()) {
      return false;
    }
    return (hour >= 0 && hour < 12)
        && (minute >= 0 && minute < 60)
        && (second >= 0 && second < 60);
  }

  public Plan select(int index) {
    return planList.get(index);
  }

  public List<Plan> listAll() {
    return planList;
  }

  public Plan update(Plan original, Plan toUpdate) {
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
}
