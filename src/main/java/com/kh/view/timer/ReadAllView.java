package com.kh.view.timer;

import static com.kh.view.timer.constant.Constant.EMPTY;
import static com.kh.view.timer.constant.Constant.READ_ALL_HEAD;

import com.kh.controller.TimerController;
import com.kh.model.vo.Timer;
import java.util.List;

public class ReadAllView extends AbstractView {

  public ReadAllView(TimerController timerController) {
    this.timerController = timerController;
  }

  @Override
  public void execute() {
    System.out.println(READ_ALL_HEAD);
    if (timerController.isEmpty()) {
      System.out.println(EMPTY);
    }
    print(timerController.readAll());
  }

  public void print(List<Timer> list) {
    for (Timer target : list) {
      System.out.println(target);
    }
  }
}
