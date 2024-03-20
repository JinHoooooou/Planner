package com.kh.Servlet;

import java.io.IOException;

import com.kh.model.dao.UserDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user/findid")
public class FindIdServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        try {
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
    
}
