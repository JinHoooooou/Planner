package com.kh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/update")
public class UpdateUserInfoServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");

        String userID = request.getParameter("userID");
		String userPW = request.getParameter("userPW");
		String userPWCheck = request.getParameter("userPWCheck");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String name = request.getParameter("name");
        String nickName = request.getParameter("nickName");
        String address = request.getParameter("address");
		        
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println(userID+","+userPW+","+userPWCheck+","+phone+","+email+","+name+","+nickName+","+address);

        
        
        System.out.println(userID+","+userPW+","+userPWCheck+","+phone+","+email+","+name+","+nickName+","+address);
	}   

}
