package main.com.kh.view.timer;

import main.com.kh.controller.TimerController;
import main.com.kh.model.vo.Timer;

public class ReadAllView extends AbstractView {

  public ReadAllView(TimerController timerController) {
    this.timerController = timerController;
  }

  @Override
  public void main() {
    System.out.println("======= 모든 Timer 보기 =======");
    if (timerController.getTimerList().isEmpty()) {
      System.out.println("저장된 Timer가 없습니다.");
    }
    for (Timer timer : timerController.getTimerList()) {
      System.out.println(timer);
    }
  }
}
