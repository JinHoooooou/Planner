package main.com.kh.view.timer;

import java.util.Scanner;

public class MainView extends AbstractView {

  private static final int CREATE = 1;
  private static final int START = 2;
  private static final int READ_ALL = 3;
  private static final int UPDATE = 4;
  private static final int DELETE = 5;
  private static final int EXIT = 9;
  private static final int DEFAULT = -1;
  private AbstractView subView;
  private int menu;

  public MainView() {
    super();
    menu = DEFAULT;
  }

  @Override
  public void main() {
    scanner = new Scanner(System.in);
    while (menu != 9) {
      printMain();
      menu = Integer.parseInt(scanner.next());
      System.out.println();
      execute();
    }
  }

  private void execute() {
    if (menu == CREATE) {
      new CreateView(timerController, scanner).main();
    } else if (menu == 2) {
//        this.viewOfReadTimer();
    } else if (menu == 3) {
//        this.viewOfUpdateTimer();
    } else if (menu == 4) {
//        this.viewOfDeleteTimer();
    } else if (menu == 9) {
//        this.viewOfExit();
    } else {
//        this.viewOfError();
    }
  }

  public void printMain() {
    System.out.println("======= MINI TIMER APP =======");
    System.out.println("1. Timer 생성");
    System.out.println("2. Timer 시작");
    System.out.println("3. Timer 보기");
    System.out.println("4. Timer 수정");
    System.out.println("5. Timer 삭제");
    System.out.println("9. 프로그램 종료");
    System.out.print("메뉴 번호 선택: ");
  }
}
