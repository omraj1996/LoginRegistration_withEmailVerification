package com.mvc.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ChatMsgController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sender = request.getParameter("sender");
		String reciever = request.getParameter("reciever");
		String message = request.getParameter("message");
		System.out.println(sender+" "+reciever+" "+message);
        
        String url="jdbc:mysql://localhost:3306/db_mvclogin"; 
        String uname="root";
        String pass=""; 
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(url,uname,pass);
            
            PreparedStatement pstmt=null;
            
            pstmt=con.prepareStatement("insert into message(sender,reciever,message) values(?,?,?)");  
            pstmt.setString(1,sender);
            pstmt.setString(2,reciever);
            pstmt.setString(2,message);
            pstmt.executeUpdate();
             
            
            pstmt.close();
            
            con.close(); //close connection
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
 
		
	}
}
