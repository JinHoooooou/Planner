package main.com.kh.view.timer;

import main.com.kh.controller.TimerController;

import java.util.Scanner;

public class CreateView extends AbstractView {

  public CreateView(TimerController controller, final Scanner scanner) {
    this.timerController = controller;
    this.scanner = scanner;
  }

  @Override
  public void main() {
    System.out.println("======= TIMER 생성 =======");
    System.out.print("Title 입력: ");
    String title = scanner.next();
    System.out.println("Timer를 입력하시겠습니까? 기본값은 20분입니다. (Y/N): ");
    String select = scanner.next();
    int hour = 0;
    int minute = 20;
    int second = 0;
    if (select.equals("Y")) {
      System.out.print("Hour 입력: ");
      hour = scanner.nextInt();
      System.out.print("Minute 입력: ");
      minute = scanner.nextInt();
      System.out.print("Second 입력: ");
      second = scanner.nextInt();
    }
    timerController.createTimer(title, hour, minute, second);
  }

  public void printMain() {

  }

  @Override
  public void input() {

  }
}
