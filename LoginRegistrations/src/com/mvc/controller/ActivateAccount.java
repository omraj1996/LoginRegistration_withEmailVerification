package com.mvc.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActivateAccount extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
		String email= request.getParameter("key1");
		String myhash= request.getParameter("key2");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db_mvclogin","root","");
			String query="select username,hash,active from user where username=? AND hash=? AND active='0'";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, email);
			stmt.setString(2, myhash);
			ResultSet result=stmt.executeQuery();
			int i=0;
			while(result.next()) {
				i++;
			}
			
			if(i==1) {
				PreparedStatement stmt1 = con.prepareStatement("UPDATE user SET active='1' WHERE username=? AND hash=?");
				stmt1.setString(1, email);
				stmt1.setString(2, myhash);
				stmt1.executeUpdate();
				response.sendRedirect("index.jsp");
			}else {
			response.sendRedirect("register.jsp");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
