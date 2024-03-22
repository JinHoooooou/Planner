package com.kh.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user/findid")
public class FindUseridServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            PrintWriter out = resp.getWriter();
            String userName = req.getParameter("userName");
            String nickname = req.getParameter("nickname");

            User user = new UserDao().findByNickname(nickname);
            
            if (user != null&&user.getUserName().equals(userName)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                out.println(user.getUserId());
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    
}
