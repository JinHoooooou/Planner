package com.kh.view.timer;

import com.kh.controller.TimerController;

import java.util.Scanner;

public abstract class AbstractView {
  protected TimerController timerController;
  protected Scanner scanner;

  public AbstractView() {
    timerController = new TimerController();
    scanner = new Scanner(System.in);
  }

  public abstract void main();


  public abstract void input();

}
