package com.kh.view.timer;

import static com.kh.view.timer.constant.Constant.EMPTY;
import static com.kh.view.timer.constant.Constant.INPUT_ERROR;
import static com.kh.view.timer.constant.Constant.UPDATE_ASK_USER_INPUT_TIME;
import static com.kh.view.timer.constant.Constant.UPDATE_HEAD;
import static com.kh.view.timer.constant.Constant.UPDATE_INPUT_HOUR;
import static com.kh.view.timer.constant.Constant.UPDATE_INPUT_MINUTE;
import static com.kh.view.timer.constant.Constant.UPDATE_INPUT_SECOND;
import static com.kh.view.timer.constant.Constant.UPDATE_INPUT_TITLE;
import static com.kh.view.timer.constant.Constant.UPDATE_RESULT_FAIL;
import static com.kh.view.timer.constant.Constant.UPDATE_RESULT_SUCCESS_FORMAT;

import com.kh.controller.TimerController;
import com.kh.model.vo.Timer;
import java.util.Scanner;

public class UpdateView extends AbstractView {

  private Timer original;
  private int index;
  private String updatedTitle;
  private int hour;
  private int minute;
  private int second;

  public UpdateView(TimerController timerController, Scanner scanner) {
    this.timerController = timerController;
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    System.out.println(UPDATE_HEAD);
    if (timerController.isEmpty()) {
      System.out.println(EMPTY);
      return;
    }
    index = inputIndex();
    inputTitle();
    setTime();
    boolean result = timerController.update(index, updatedTitle, hour, minute, second);

    print(result);
  }

  private void inputTitle() {
    System.out.print(UPDATE_INPUT_TITLE);
    updatedTitle = scanner.nextLine();
    System.out.println();
  }

  private void setTime() {
    original = Timer.copy(timerController.readOne(index));
    if (isTimeUserInput()) {
      System.out.print(UPDATE_INPUT_HOUR);
      hour = Integer.parseInt(scanner.nextLine());
      System.out.println();

      System.out.print(UPDATE_INPUT_MINUTE);
      minute = Integer.parseInt(scanner.nextLine());
      System.out.println();

      System.out.print(UPDATE_INPUT_SECOND);
      second = Integer.parseInt(scanner.nextLine());
      System.out.println();
    } else {
      hour = original.getHours();
      minute = original.getMinutes();
      second = original.getSeconds();
    }
  }

  private boolean isTimeUserInput() {
    while (true) {
      System.out.print(UPDATE_ASK_USER_INPUT_TIME);
      String isUserInput = scanner.nextLine();
      System.out.println();

      if (isUserInput.equals("Y")) {
        return true;
      } else if (isUserInput.equals("N")) {
        return false;
      }
      System.out.println(INPUT_ERROR);
    }
  }

  private void print(boolean result) {
    System.out.println(result
        ? String.format(UPDATE_RESULT_SUCCESS_FORMAT, original, timerController.readOne(index))
        : UPDATE_RESULT_FAIL);
  }
}
