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
      System.out.println(Constant.EMPTY);
      return;
    }
    Timer timer = timerController.readOne(inputIndex());
    print(timer);
  }

  private int inputIndex() {
    while (true) {
      System.out.print(Constant.INPUT_INDEX);
      int result = Integer.parseInt(scanner.nextLine());
      System.out.println();

      if (result >= 0 && result < timerController.size()) {
        return result;
      }
      System.out.println(Constant.INPUT_ERROR);
    }
  }

  public void print(Timer timer) {
    System.out.println(timer);
  }

}
