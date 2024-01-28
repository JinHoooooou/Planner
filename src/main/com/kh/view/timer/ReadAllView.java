package main.com.kh.view.timer;

import main.com.kh.controller.TimerController;
import main.com.kh.model.vo.Timer;

import static main.com.kh.view.timer.constant.Constant.READ_ALL_HEAD;
import static main.com.kh.view.timer.constant.Constant.EMPTY;

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
    for (Timer timer : timerController.getTimerList()) {
      System.out.println(timer);
    }
  }
}
