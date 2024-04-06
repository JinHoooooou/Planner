package com.kh.controller;

import jakarta.servlet.http.HttpSession;

public class UserSessionUtils {

  public static final String USER_SESSION_KEY = "userId";

  public static String getUserIdFromSession(HttpSession session) {
    Object userId = session.getAttribute(USER_SESSION_KEY);
    return userId == null ? null : (String) userId;
  }

  public static boolean isSignIn(HttpSession session) {
    return session != null && getUserIdFromSession(session) != null;
  }
}
