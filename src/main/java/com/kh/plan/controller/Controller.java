package com.kh.plan.controller;

import com.kh.server.HttpRequest;
import com.kh.server.HttpResponse;

public interface Controller {

  void service(HttpRequest request, HttpResponse response);
}
