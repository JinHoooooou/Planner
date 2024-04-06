package com.kh.controller.plan;

import com.kh.constant.Message;
import com.kh.controller.RestController;
import com.kh.controller.UserSessionUtils;
import com.kh.model.dao.DetailPlanDao;
import com.kh.model.dao.PlanDao;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.Plan;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ListPlanController implements RestController {

  private final UserDao userDao = new UserDao();
  private final PlanDao planDao = new PlanDao();
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
      String userId = UserSessionUtils.getUserIdFromSession(session);
      String nickname = userDao.findByUserId(userId).getNickname();
      List<Plan> plans = planDao.findByWriterOrderByEndDate(userId);

      JSONObject data = new JSONObject();
      data.put("nickname", nickname);
      data.put("planList", buildJsonArray(plans));
      responseBody.put("data", data);
      responseBody.put("status", HttpServletResponse.SC_OK);
    } catch (Exception e) {
      responseBody.put("message", e.getLocalizedMessage());
      responseBody.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    return responseBody;
  }

  private JSONArray buildJsonArray(List<Plan> plans) {
    JSONArray result = new JSONArray();
    for (Plan plan : plans) {
      boolean hasDetails = !detailDao
          .findByWriterAndPlanIdOrderByDetailPlanId(plan.getWriter(), plan.getPlanId()).isEmpty();
      result.put(plan.parseJson().put("hasDetails", hasDetails));
    }
    return result;
  }
}
