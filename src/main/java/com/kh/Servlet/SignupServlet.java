package com.kh.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.json.JSONObject;

import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;

@WebServlet("/user/signup")
public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        try {
            User user = User.dto(req);
            user.validate();

            new UserDao().save(user);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            String message = e.getMessage();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(new JSONObject().put("message", message).toString());
        }
        resp.getWriter().flush();
        resp.getWriter().close();
    }  
}
    

