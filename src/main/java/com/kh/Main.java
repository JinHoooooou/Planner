package com.kh;

import java.io.File;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;


public class Main {

  private static final String WEB_APP_DIR_LOCATION = "src/main/webapp";
  private static final String CLASS_LOCATION = "out/production/classes";
  private static final String WEB_APP_MOUNT = "/WEB-INF/classes";

  public static void main(String[] args) throws Exception {
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);

    Context context = tomcat.addWebapp("/", new File(WEB_APP_DIR_LOCATION).getAbsolutePath());
    File additionWebInfClasses = new File(CLASS_LOCATION);
    WebResourceRoot resources = new StandardRoot(context);
    resources.addPreResources(
        new DirResourceSet(resources, WEB_APP_MOUNT, additionWebInfClasses.getAbsolutePath(), "/"));
    context.setResources(resources);

    tomcat.getConnector();

    tomcat.start();
    tomcat.getServer().await();
  }
}
