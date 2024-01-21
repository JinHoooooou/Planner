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

  public static Timer createDefault(String title) {
    return new Timer(title);
  }

  private Timer(String title) {
    this.title = title;
    this.setDefaultTimer();
  }

  private Timer(String title, int hours, int minutes, int seconds) {
    this.title = title;
    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
  }

  private void setDefaultTimer() {
    this.hours = 0;
    this.minutes = 20;
    this.seconds = 0;
  }

  public static Timer userInput(String title, Scanner scanner) {
    System.out.print("Hour: ");
    int hour = Integer.parseInt(scanner.next());
    System.out.println();

    System.out.print("Minute: ");
    int minute = Integer.parseInt(scanner.next());
    System.out.println();

    System.out.print("Second: ");
    int second = Integer.parseInt(scanner.next());
    System.out.println();

    return new Timer(title, hour, minute, second);
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
}
