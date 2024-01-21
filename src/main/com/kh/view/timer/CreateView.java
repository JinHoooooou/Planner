package main.com.kh.view.timer;

import main.com.kh.controller.TimerController;

import java.util.HashMap;
import java.util.Scanner;

public class CreateView extends AbstractView {

  private String title;
  private int hour;
  private int minute;
  private int second;

  private static HashMap<String, Boolean> userInput = new HashMap<>();
  private static HashMap<String, Integer> defaultTimes = new HashMap<>();

  static {
    userInput.put("Y", true);
    userInput.put("N", false);
    defaultTimes.put("Hour", 0);
    defaultTimes.put("Minute", 20);
    defaultTimes.put("Second", 0);
  }

  public CreateView(TimerController controller, final Scanner scanner) {
    this.timerController = controller;
    this.scanner = scanner;
    main();
  }

  @Override
  public void main() {
    System.out.println("======= TIMER 생성 =======");
    inputTimerSetting();
    print(timerController.createTimer(title, hour, minute, second));
  }

  private void inputTimerSetting() {
    inputTitle();
    inputDetailTimerSetting();
  }

  private void inputTitle() {
    System.out.print("Title 입력: ");
    title = scanner.next();
    System.out.println();
  }

  private void inputDetailTimerSetting() {
    while (true) {
      System.out.print("Timer를 입력하시겠습니까? 기본값은 20분입니다. (Y/N): ");
      Boolean isUserInput = userInput.get(scanner.next());
      System.out.println();

      if (isUserInput != null) {
        hour = inputTime("Hour", isUserInput);
        minute = inputTime("Minute", isUserInput);
        second = inputTime("Second", isUserInput);
        break;
      }

      System.out.println("잘못 입력하였습니다. 다시 입력해주세요.");
    }
  }

  private int inputTime(String type, Boolean isUserInput) {
    if (!isUserInput) {
      return defaultTimes.get(type);
    }

    System.out.print(type + ": ");
    int result = Integer.parseInt(scanner.next());
    System.out.println();
    return result;
  }

  private void print(boolean result) {
    System.out.println(result ? "새 타이머를 생성하였습니다." : "새 타이머 생성에 실패하였습니다. 다시 입력해주세요");
  }

  @Override
  public void input() {

  }
}
