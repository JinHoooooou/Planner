package com.kh;

import com.kh.server.WebServer;

public class Main {

  public static void main(String[] args) throws Exception {
    WebServer webServer = new WebServer();
    webServer.start(args);
//    if (args[0].equals("web")) {
//      WebServer webServer = new WebServer();
//      webServer.start(args);
//    } else {
//      MainView mainView = new MainView();
//      mainView.execute();
//    }
  }
}
