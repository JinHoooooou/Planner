package main.com.kh.view.timer;

import main.com.kh.controller.TimerController;
import main.com.kh.model.vo.Timer;

import java.util.List;
import java.util.Scanner;

public class ReadOneView extends AbstractView {

  public ReadOneView(TimerController timerController, Scanner scanner) {
    this.timerController = timerController;
    this.scanner = scanner;
  }

  @Override
  public void main() {
    System.out.println("======= TIMER 시작 =======");
    List<Timer> timerList = timerController.getTimerList();
    if (timerList.isEmpty()) {
      System.out.println("저장된 Timer가 없습니다.");
      return;
    }
    print(timerList.get(inputIndex()));
  }

  private int inputIndex() {
    List<Timer> timerList = timerController.getTimerList();
    while (true) {
      System.out.printf("시작할 Timer Id를 입력하세요: (0~%d)", timerList.size() - 1);
      int result = Integer.parseInt(scanner.nextLine());
      System.out.println();

      if (result >= 0 && result < timerList.size()) {
        return result;
      }
      System.out.println("Index를 잘못 입력하였습니다. 다시 입력해주세요.");
    }
  }

  public void print(Timer timer) {
    System.out.println(timer);
  }

}
