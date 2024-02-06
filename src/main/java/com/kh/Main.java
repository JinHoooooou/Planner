package com.kh;

import com.kh.view.plan.MainView;
import java.util.concurrent.TimeUnit;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    int startSeconds = 72;
    int currentSeconds = startSeconds;
    System.out.println("Concentration");
    while (true) {
      String parsed = parseToTime(currentSeconds);
      System.out.println(parsed);
      TimeUnit.SECONDS.sleep(1);
      if (currentSeconds == 0) {
        break;
      }
      currentSeconds--;
    }


    MainView mainView = new MainView();
    mainView.execute();
  }

  private static String parseToTime(int currentSeconds) {
    int minute = currentSeconds / 60;
    int second = currentSeconds % 60;

    return String.format("%02d:%02d", minute, second);

  }
}
