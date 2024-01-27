package main.com.kh.model.vo;

import java.util.Scanner;

public class Timer {

  private String title;
  private int hours;
  private int minutes;
  private int seconds;

  public Timer() {

  }

  public static Timer create(String title, int hour, int minute, int second) {
    return new Timer(title, hour, minute, second);
  }

  private Timer(String title, int hours, int minutes, int seconds) {
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

  @Override
  public String toString() {
    return String.format("Title: %s (%d:%d:%d)", title, hours, minutes, seconds);
  }
}
