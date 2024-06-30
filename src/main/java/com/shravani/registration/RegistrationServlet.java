package com.shravani.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname=request.getParameter("name");//the name in quotes is the same reference given in registration.jsp page
		String upwd=request.getParameter("pass");
		String reupwd=request.getParameter("re_pass");
		String uemail=request.getParameter("email");
		String umobile=request.getParameter("contact");
		
		RequestDispatcher rd=null; 
		if(uname==null || uname=="") {
			request.setAttribute("status", "invalidName");
			rd=request.getRequestDispatcher("registration.jsp");
			rd.forward(request, response);
		}
		if(upwd==null || upwd=="") {
			request.setAttribute("status", "invalidUpwd");
			rd=request.getRequestDispatcher("registration.jsp");
			rd.forward(request, response);
		}else if(!upwd.equals(reupwd)){
			request.setAttribute("status", "invalidConfirmPassword");
			rd=request.getRequestDispatcher("registration.jsp");
			rd.forward(request, response);
		}
		if(uemail==null || uemail=="") {
			request.setAttribute("status", "invalidUemail");
			rd=request.getRequestDispatcher("registration.jsp");
			rd.forward(request, response);
		}
		if(umobile==null || umobile=="") {
			request.setAttribute("status", "invalidMobile");
			rd=request.getRequestDispatcher("registration.jsp");
			rd.forward(request, response);
		}else if(umobile.length()>10){
			request.setAttribute("status", "InvalidMobileLength");
			rd=request.getRequestDispatcher("registration.jsp");
			rd.forward(request, response);
		}
		//PrintWriter out = response.getWriter();
		//out.println(uname);
		//out.println(upwd);
		//out.println(uemail);
		//out.println(umobile);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mvcsignup?useSSL=false","root","pass123");
			PreparedStatement ps=con.prepareStatement("insert into users (uname, upwd, uemail, umobile) values (?,?,?,?)");
			ps.setString(1,  uname);
			ps.setString(2,  upwd);
			ps.setString(3,  uemail);
			ps.setString(4,  umobile);
			int rowCount= ps.executeUpdate();
			rd=request.getRequestDispatcher("registration.jsp");
			if(rowCount > 0) {
				request.setAttribute("status", "success");
			}
			else
			{
				request.setAttribute("status", "failed");
			}
			rd.forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}

}
