package main.com.kh.controller;

import main.com.kh.model.vo.Timer;

import java.util.ArrayList;
import java.util.List;

public class TimerController {

  private static final int DEFAULT_HOUR = 0;
  private static final int DEFAULT_MINUTE = 20;
  private static final int DEFAULT_SECOND = 0;
  List<Timer> timerList = new ArrayList<>();

  public boolean create(String title) {
    return this.create(title, DEFAULT_HOUR, DEFAULT_MINUTE, DEFAULT_SECOND);
  }

  public boolean create(String title, int hour, int minute, int second) {
    if (!isValid(title, hour, minute, second)) {
      return false;
    }
    Timer newTimer = Timer.create(title, hour, minute, second);
    timerList.add(newTimer);

    return true;
  }

  private static boolean isValid(String title, int hour, int minute, int second) {
    if (title.isEmpty()) {
      return false;
    }
    return (hour >= 0 && hour < 13)
            && (minute >= 0 && minute < 60)
            && (second >= 0 && second < 60);
  }

  public Timer getTimer(int index) {
    if (index < 0 || index >= size()) {
      throw new IndexOutOfBoundsException();
    }
    return timerList.get(index);
  }

  public boolean updateTimer(int index, String updatedTitle, int hour, int minute, int second) {
    Timer original = getTimer(index);
    original.setTitle(updatedTitle);
    original.setHours(hour);
    original.setMinutes(minute);
    original.setSeconds(second);

    return true;
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
