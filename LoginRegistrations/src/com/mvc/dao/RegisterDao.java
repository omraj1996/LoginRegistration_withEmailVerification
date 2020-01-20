package com.mvc.dao;

import com.mvc.bean.RegisterBean;
import com.mvc.controller.Mailer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterDao {
	public String authorizeRegister(RegisterBean registerBean) // create authorizeRegister()function
	{
		String firstname = registerBean.getFirstname();
		String lastname = registerBean.getLastname();
		String username = registerBean.getUsername(); // get all value through registerBean object and store in												// temporary variable
		String password = registerBean.getPassword();
		String myhash = registerBean.getMyhash();  

		String url = "jdbc:mysql://localhost:3306/db_mvclogin"; // database connection url string
		String uname = "root"; // database username
		String pass = ""; // database password

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // load driver
			Connection con = DriverManager.getConnection(url, uname, pass); // create connection

			String dbusername = ""; // create two variable for use next process

			PreparedStatement pstmt1 = null;
			pstmt1 = con.prepareStatement("select username from user where username =?");
			pstmt1.setString(1, username);
			ResultSet rs = pstmt1.executeQuery();
			while (rs.next()) {
				dbusername = rs.getString("username"); // fetchable database record username and password store in this
														// two variable dbusername,dbpassword above created
				if (username.equals(dbusername)) // apply if condition check for fetchable database username and
													// password are match for user input side type in textbox
				{
					return "Username already taken."; // if valid condition return string "SUCCESS LOGIN"
				}
			}

			/*JavaEmail javaEmail = new JavaEmail();
	        javaEmail.setMailServerProperties();
	        javaEmail.draftEmailMessage();
	        javaEmail.sendEmail();*/
			
			PreparedStatement pstmt = null; // create statement

			pstmt = con.prepareStatement("insert into user(firstname,lastname,username,password,hash) values(?,?,?,?,?)"); // sql
																													// insert
																													// query
			pstmt.setString(1, firstname);
			pstmt.setString(2, lastname);
			pstmt.setString(3, username);
			pstmt.setString(4, password);
			pstmt.setString(5, myhash);
			int i=pstmt.executeUpdate(); // execute query
			
			if(i!=0) {
				
				Mailer me=  new Mailer(username,myhash);
				me.sendMail();
				return "SUCCESS";
			}

			pstmt.close(); // close statement

			con.close(); // close connection
			 
			//return "SUCCESS REGISTER"; // if valid return string "SUCCESS REGISTER"
			
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return "FAIL REGISTER"; // if invalid return string "FAIL REGISTER"
	}
}