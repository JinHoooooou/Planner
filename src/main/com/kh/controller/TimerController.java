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

  public Timer getTimer(int index) {
    if(index < 0 || index >= size()) {
      throw new IndexOutOfBoundsException();
    }
    return timerList.get(index);
  }

  public int size() {
    return timerList.size();
  }

  public boolean isEmpty() {
    return timerList.isEmpty();
  }

  public List<Timer> getTimerList() {
    return timerList;
  }

  public void setTimerList(List<Timer> list) {
    this.timerList = list;
  }
}
