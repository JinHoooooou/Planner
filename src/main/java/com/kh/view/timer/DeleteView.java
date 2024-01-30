package main.com.kh.view.timer;

import main.com.kh.controller.TimerController;
import main.com.kh.view.timer.constant.Constant;

import java.util.Scanner;

import static main.com.kh.view.timer.constant.Constant.*;
import static main.com.kh.view.timer.constant.Constant.CREATE_RESULT_FAIL;

public class DeleteView extends AbstractView {


  public DeleteView(TimerController timerController, Scanner scanner) {
    this.timerController = timerController;
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    System.out.println(DELETE_HEAD);
    if (timerController.isEmpty()) {
      System.out.println(EMPTY);
      return;
    }
    int index = inputIndex();
    print(timerController.delete(index));
  }

  private void print(boolean result) {
    System.out.println(result ? DELETE_SUCCESS : DELETE_FAIL);
  }
}
