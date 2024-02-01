package com.kh.model.vo;

public class Plan {

  private String title;
  private int timerCount;
  private String memo;
  private boolean clear;


  private int hours;
  private int minutes;
  private int seconds;

  public Plan() {
    this.timerCount = 0;
    this.clear = false;
    this.memo = "";
  }

  public static Plan create(String title, String memo) {
    return new Plan(title, memo);
  }

  public static Plan create(String title, int hour, int minute, int second) {
    return new Plan(title, hour, minute, second);
  }

  public static Plan copy(Plan original) {
    return new Plan(original);
  }


  private Plan(String title) {
    this.title = title;
    this.timerCount = 0;
    this.clear = false;
    this.memo = "";
  }

  private Plan(String title, String memo) {
    super();
    this.title = title;
    this.memo = memo;
  }

  private Plan(String title, int hours, int minutes, int seconds) {
    this.title = title;
    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
  }

  private Plan(Plan original) {
    this.title = original.getTitle();
    this.hours = original.getHours();
    this.minutes = original.getMinutes();
    this.seconds = original.getSeconds();
  }

  public String getTitle() {
    return title;
  }

  public int getTimerCount() {
    return timerCount;
  }

  public String getMemo() {
    return memo;
  }

  public boolean isClear() {
    return clear;
  }

  public boolean getClear() {
    return clear;
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

  public void setTimerCount(int timerCount) {
    this.timerCount = timerCount;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public void setClear(boolean clear) {
    this.clear = clear;
  }

  @Override
  public String toString() {
    return this.title;
  }

  public Plan update(Plan toUpdate) {
    if (toUpdate.getTitle().isEmpty()) {
      return null;
    }
    setTitle(toUpdate.getTitle());
    setMemo(toUpdate.getMemo());
    setTimerCount(toUpdate.getTimerCount());
    setClear(toUpdate.getClear());

    return this;
  }
}
