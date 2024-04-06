package com.kh.controller;

import com.kh.controller.plan.CompletePlanController;
import com.kh.controller.plan.CreatePlanController;
import com.kh.controller.plan.ListPlanController;
import com.kh.controller.user.DeleteUserController;
import com.kh.controller.user.GetUserInfoController;
import com.kh.controller.user.NicknameDuplicateController;
import com.kh.controller.user.SignInController;
import com.kh.controller.user.SignOutController;
import com.kh.controller.user.SignUpController;
import com.kh.controller.user.UpdateUserController;
import com.kh.controller.user.UserIdDuplicateController;
import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

  Map<String, RestController> map = new HashMap<>();

  public void initMapping() {
    map.put("/api/user/duplicate/userid", new UserIdDuplicateController());
    map.put("/api/user/duplicate/nickname", new NicknameDuplicateController());
    map.put("/api/user/signup", new SignUpController());
    map.put("/api/user/signin", new SignInController());
    map.put("/api/user/info", new GetUserInfoController());
    map.put("/api/user/delete", new DeleteUserController());
    map.put("/api/user/update", new UpdateUserController());
    map.put("/api/user/signout", new SignOutController());

    map.put("/api/plan/list", new ListPlanController());
    map.put("/api/plan/create", new CreatePlanController());
    map.put("/api/plan/complete", new CompletePlanController());
  }

  public RestController findController(String url) {
    return this.map.get(url);
  }
}
