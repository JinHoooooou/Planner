package com.kh.plan.view;

import com.kh.plan.controller.PlanController;
import com.kh.plan.model.vo.Plan;
import java.util.concurrent.TimeUnit;

public class ConcentrateView extends AbstractView {

  public int defaultHour = 0;
  public int defaultMinute = 0;
  public int defaultSecond = 2;
  private Plan target;

  public ConcentrateView(PlanController planController, Plan target) {
    this.planController = planController;
    this.target = target;
  }

  @Override
  public void execute() {
    int timerSec = toSec(defaultHour, defaultMinute, defaultSecond);
    while (timerSec >= 0) {
      String time = parse(timerSec);
      System.out.println(time);
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      timerSec--;
    }
    planController.countTimer(target);
    System.out.println(target.getTimerCount() + "뽀모도로 끝!");
  }

  private String parse(int timerSec) {
    int hour = timerSec / 3600;
    int minute = timerSec / 60;
    int second = timerSec % 60;

    return String.format("%02d:%02d:%02d", hour, minute, second);
  }

  private int toSec(int hour, int minute, int second) {
    return (hour * 3600) + (minute * 60) + second;
  }
}
