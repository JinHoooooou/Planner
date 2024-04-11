package com.kh.controller.detail;

import com.kh.constant.Message;
import com.kh.controller.RestController;
import com.kh.controller.UserSessionUtils;
import com.kh.model.dao.DetailPlanDao;
import com.kh.model.vo.DetailPlan;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ListDetailController implements RestController {

  private final DetailPlanDao detailDao = new DetailPlanDao();

  @Override
  public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
    JSONObject responseBody = new JSONObject();
    HttpSession session = request.getSession(false);

    if (!UserSessionUtils.isSignIn(session)) {
      responseBody.put("message", Message.INVALID_SESSION);
      responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
      return responseBody;
    }

    try {
      int planId = Integer.parseInt(request.getParameter("planId"));

      List<DetailPlan> details = detailDao.findByPlanIdOrderByDetailPlanId(planId);
      JSONObject data = new JSONObject();
      data.put("planId", planId);
      data.put("detailList", buildJsonArray(details));

      responseBody.put("data", data);
      responseBody.put("status", HttpServletResponse.SC_OK);
    } catch (RuntimeException e) {
      responseBody.put("message", Message.INVALID_REQUEST);
      responseBody.put("status", HttpServletResponse.SC_BAD_REQUEST);
    }

    return responseBody;
  }

  private JSONArray buildJsonArray(List<DetailPlan> details) {
    JSONArray result = new JSONArray();
    for (DetailPlan detail : details) {
      result.put(detail.parseJson());
    }
    return result;
  }
}
