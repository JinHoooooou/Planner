package com.kh.servlet.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.json.JSONObject;

@WebFilter(value = {"/details", "/detail/*"})
public class SessionFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpSession session = ((HttpServletRequest) request).getSession(false);
    if (session == null || session.getAttribute("userId") == null) {
      ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write(new JSONObject().toString());
      return;
    }

    chain.doFilter(request, response);
  }
}
