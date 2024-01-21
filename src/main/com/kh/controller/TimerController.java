package main.com.kh.controller;

import main.com.kh.model.vo.Timer;

import java.util.ArrayList;
import java.util.List;

public class TimerController {

  List<Timer> timerList = new ArrayList<>();

  public boolean createTimer(String title, int hour, int minute, int second) {
    return createTimer(Timer.create(title, hour, minute, second));
  }

  public boolean createTimer(Timer newTimer) {
    timerList.add(newTimer);
    return true;
  }


  public List<Timer> getTimerList() {
    return timerList;
  }

}
