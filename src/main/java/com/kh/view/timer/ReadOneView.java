package com.kh.view.timer;

import static com.kh.view.timer.constant.Constant.EMPTY;
import static com.kh.view.timer.constant.Constant.READ_ONE_HEAD;

import com.kh.controller.TimerController;
import com.kh.model.vo.Timer;
import java.util.Scanner;

public class ReadOneView extends AbstractView {

  public ReadOneView(TimerController timerController, Scanner scanner) {
    this.timerController = timerController;
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    System.out.println(READ_ONE_HEAD);
    if (timerController.isEmpty()) {
      System.out.println(EMPTY);
      return;
    }
    Timer timer = timerController.readOne(inputIndex());
    print(timer);
  }

  public void print(Timer timer) {
    System.out.println(timer);
  }

}
