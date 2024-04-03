package com.kh.servlet;

import com.kh.servlet.user.UserIdDuplicateController;
import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

  Map<String, RestController> map = new HashMap<>();

  public void initMapping() {
    map.put("/api/user/duplicate/userid", new UserIdDuplicateController());
  }

  public RestController findController(String url) {
    return this.map.get(url);
  }
}
