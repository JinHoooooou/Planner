package com.kh.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Plan {

  private String title;
  private int timerCount;
  private String memo;
  private boolean clear;

  public Plan() {
    this.timerCount = 0;
    this.clear = false;
    this.memo = "";
  }

  private Plan(String title) {
    this.title = title;
    this.timerCount = 0;
    this.clear = false;
    this.memo = "";
  }

  private Plan(String title, String memo) {
    this.title = title;
    this.memo = memo;
  }

  private Plan(Plan original) {
    this.title = original.getTitle();
    this.memo = original.getMemo();
    this.timerCount = original.getTimerCount();
    this.clear = original.isClear();
  }

  public static Plan create(String title) {
    return new Plan(title, "");
  }

  public static Plan create(String title, String memo) {
    return new Plan(title, memo);
  }

  public static Plan copy(Plan original) {
    return new Plan(original);
  }

  @Override
  public String toString() {
    return "title: " + this.getTitle() + " / memo: " + this.getMemo();
  }

  public Plan update(Plan toUpdate) {
    setTitle(toUpdate.getTitle());
    setMemo(toUpdate.getMemo());
    setTimerCount(toUpdate.getTimerCount());
    setClear(toUpdate.isClear());

    return this;
  }
}
