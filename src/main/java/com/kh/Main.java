package com.kh;

import com.kh.server.WebServer;


public class Main {

  private static final String WEB_APP_DIR_LOCATION = "src/main/resources";
  private static final String CLASS_LOCATION = "build/classes";
  private static final String WEB_APP_VIRTUAL_PATH = "/WEB-INF/classes";

  public static void main(String[] args) throws Exception {
    WebServer webServer = new WebServer();
    webServer.start(args);
  }
}
