package main.com.kh.view.timer;

import java.util.Scanner;

public class MainView extends AbstractView {

  private static final int DEFAULT = -1;
  private int menu;
  private AbstractView subView;

  public MainView() {
    super();
    menu = DEFAULT;
  }

  @Override
  public void execute() {
    scanner = new Scanner(System.in);
    while (menu != 9) {
      printMain();
      inputSubMenu();
      subView.execute();
    }
  }

  private void inputSubMenu() {
    menu = Integer.parseInt(scanner.nextLine());
    System.out.println();
    subView = AbstractView.subView(menu, timerController, scanner);
  }

  public void printMain() {
    System.out.println("======= MINI TIMER APP =======");
    System.out.println("1. Timer 생성");
    System.out.println("2. Timer 시작");
    System.out.println("3. 모든 Timer 보기");
    System.out.println("4. Timer 수정");
    System.out.println("5. Timer 삭제");
    System.out.println("9. 프로그램 종료");
    System.out.print("메뉴 번호 선택: ");
  }
}
