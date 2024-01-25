package main.com.kh.view.timer;

import main.com.kh.controller.TimerController;
import main.com.kh.model.vo.Timer;

import java.util.Scanner;

public class ReadOneView extends AbstractView {

  public ReadOneView(TimerController timerController, Scanner scanner) {
    this.timerController = timerController;
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    System.out.println("======= TIMER 시작 =======");
    if (timerController.isEmpty()) {
      System.out.println("저장된 Timer가 없습니다.");
      return;
    }
    Timer timer = timerController.getTimer(inputIndex());
    print(timer);
  }

  private int inputIndex() {
    while (true) {
      System.out.printf("시작할 Timer Id를 입력하세요: (0~%d)", timerController.size() - 1);
      int result = Integer.parseInt(scanner.nextLine());
      System.out.println();

      if (result >= 0 && result < timerController.size()) {
        return result;
      }
      System.out.println("Index를 잘못 입력하였습니다. 다시 입력해주세요.");
    }
  }

  public void print(Timer timer) {
    System.out.println(timer);
  }

}
