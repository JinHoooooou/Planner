package com.kh.servlet;

import com.kh.servlet.user.NicknameDuplicateController;
import com.kh.servlet.user.SignUpController;
import com.kh.servlet.user.UserIdDuplicateController;
import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

  Map<String, RestController> map = new HashMap<>();

  public void initMapping() {
    map.put("/api/user/duplicate/userid", new UserIdDuplicateController());
    map.put("/api/user/duplicate/nickname", new NicknameDuplicateController());
    map.put("/api/user/signup", new SignUpController());
  }

  public RestController findController(String url) {
    return this.map.get(url);
  }
}
