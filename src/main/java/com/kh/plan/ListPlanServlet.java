package com.kh.plan;

import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

@WebServlet("/plan/list")
public class ListPlanServlet extends HttpServlet {

  private TemplateEngine templateEngine;

  @Override
  public void init() throws ServletException {
    super.init();
    FileTemplateResolver resolver = new FileTemplateResolver();
    resolver.setTemplateMode("HTML");
    resolver.setPrefix("src/main/resources/plan"); // Set the path to your templates
    resolver.setSuffix(".html");
    resolver.setCacheable(false); // Disable caching for development

    templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(resolver);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    List<Plan> plans = new PlanDao().findByUsersIdOrderByEndDate(req.getParameter("userId"));
    Context context = new Context();
    context.setVariable("plans", plans);
    resp.setContentType("text/html;charset=UTF-8");
    templateEngine.process("/list", context, resp.getWriter());
  }

}
