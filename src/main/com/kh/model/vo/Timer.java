package main.com.kh.model.vo;

import java.util.Scanner;

public class Timer {

  private String title;
  private int hours;
  private int minutes;
  private int seconds;
  private String memo;

  public Timer() {
    this.setDefaultTimer();
  }

  private void setDefaultTimer() {
    this.hours = 0;
    this.minutes = 20;
    this.seconds = 0;
  }

  public Timer(String title, int hours, int minutes, int seconds) {
    this.title = title;
    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getHours() {
    return hours;
  }

  public void setHours(int hours) {
    this.hours = hours;
  }

  public int getMinutes() {
    return minutes;
  }

  public void setMinutes(int minutes) {
    this.minutes = minutes;
  }

  public int getSeconds() {
    return seconds;
  }

  public void setSeconds(int seconds) {
    this.seconds = seconds;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }
}
