package main.com.kh.view.timer;

import main.com.kh.controller.TimerController;
import main.com.kh.model.vo.Timer;
import main.com.kh.view.timer.constant.Constant;

import java.util.Scanner;

import static main.com.kh.view.timer.constant.Constant.*;

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
