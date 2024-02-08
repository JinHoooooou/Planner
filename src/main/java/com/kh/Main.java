package com.kh;

import com.kh.server.WebServer;

public class Main {

  public static void main(String[] args) throws Exception {
    WebServer webServer = new WebServer();
    webServer.start(args);
//    MainView mainView = new MainView();
//    mainView.execute();
  }
}
