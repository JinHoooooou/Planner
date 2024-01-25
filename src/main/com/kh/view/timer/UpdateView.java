package main.com.kh.view.timer;

import main.com.kh.controller.TimerController;

import java.util.Scanner;

public class UpdateView extends AbstractView {

  private int hour;
  private int minute;
  private int second;

  public UpdateView(TimerController timerController, Scanner scanner) {
    this.timerController = timerController;
    this.scanner = scanner;
  }

  @Override
  public void main() {
    System.out.println("======= TIMER 수정 =======");
    if (timerController.isEmpty()) {
      System.out.println("저장된 Timer가 없습니다.");
      return;
    }

    int originalIndex = inputIndex();
    print(updateTimer(originalIndex));
  }

  private boolean updateTimer(int index) {
    System.out.print("수정할 Title 입력: ");
    String updatedTitle = scanner.nextLine();
    System.out.println();

    hour = inputTime("Hour");
    minute = inputTime("Minute");
    second = inputTime("Second");
    return timerController.updateTimer(index, updatedTitle, hour, minute, second);
  }

  private int inputTime(String type) {
    System.out.print("수정할 " + type + " 입력: ");
    int result = Integer.parseInt(scanner.nextLine());
    System.out.println();
    return result;
  }

  private int inputIndex() {
    while (true) {
      System.out.printf("수정할 Timer Id를 입력하세요: (0~%d)", timerController.size() - 1);
      int result = Integer.parseInt(scanner.nextLine());
      System.out.println();

      if (result >= 0 && result < timerController.size()) {
        return result;
      }
      System.out.println("Index를 잘못 입력하였습니다. 다시 입력해주세요.");
    }
  }

  private void print(boolean result) {
    System.out.println(result ? "타이머를 수정하였습니다." : "타이머 수정을 실패하였습니다.");
  }
}
