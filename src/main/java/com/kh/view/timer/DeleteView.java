package com.kh.view.timer;

import com.kh.controller.TimerController;

import java.util.Scanner;

import static com.kh.view.timer.constant.Constant.*;

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
