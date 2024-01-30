package main.com.kh.view.timer;

import main.com.kh.controller.TimerController;

import java.util.Scanner;

import static main.com.kh.view.timer.constant.Constant.*;

public class CreateView extends AbstractView {

  private String title;
  private int hour;
  private int minute;
  private int second;

  public CreateView(TimerController controller, final Scanner scanner) {
    this.timerController = controller;
    this.scanner = scanner;
    hour = DEFAULT_HOUR;
    minute = DEFAULT_MINUTE;
    second = DEFAULT_SECOND;
  }

  @Override
  public void execute() {
    System.out.println(CREATE_HEAD);
    inputTitle();
    setTime();
    boolean result = timerController.create(title, hour, minute, second);
    print(result);
  }
  private void inputTitle() {
    System.out.print(CREATE_INPUT_TITLE);
    title = scanner.nextLine();
    System.out.println();
  }

  private void setTime() {
    if (isTimeUserInput()) {
      System.out.print(CREATE_INPUT_HOUR);
      hour = Integer.parseInt(scanner.nextLine());
      System.out.println();

      System.out.print(CREATE_INPUT_MINUTE);
      minute = Integer.parseInt(scanner.nextLine());
      System.out.println();

      System.out.print(CREATE_INPUT_SECOND);
      second = Integer.parseInt(scanner.nextLine());
      System.out.println();
    } else {
      hour = DEFAULT_HOUR;
      minute = DEFAULT_MINUTE;
      second = DEFAULT_SECOND;
    }
  }
  private boolean isTimeUserInput() {
    while (true) {
      System.out.print(CREATE_ASK_USER_INPUT_TIME);
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
    System.out.println(result ?
            String.format(CREATE_RESULT_SUCCESS_FORMAT, timerController.readOne(timerController.size() - 1))
            : CREATE_RESULT_FAIL);
  }
}
