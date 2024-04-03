package com.kh.servlet;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

  Map<String, RestController> map = new HashMap<>();

  public void initMapping() {
  }

  public RestController findController(String url) {
    return this.map.get(url);
  }
}
