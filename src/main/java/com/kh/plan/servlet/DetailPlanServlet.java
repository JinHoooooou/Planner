package com.kh.plan.servlet;

import com.kh.plan.model.vo.Plan;
import com.kh.plan.service.PlanService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

@WebServlet("/plan/details")
public class DetailPlanServlet extends HttpServlet {

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
    String id = req.getParameter("id");
    Plan plan = new PlanService().findById(Integer.parseInt(id));
    Context context = new Context();
    context.setVariable("plan", plan);
    resp.setContentType("text/html;charset=UTF-8");
    templateEngine.process("/details", context, resp.getWriter());
  }
}
