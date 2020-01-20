package com.mvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpSession;

import com.mvc.bean.ChangePasswordBean;

public class ChangePasswordDao {
	public String authorizeLogin(ChangePasswordBean changePasswordBean) //create authorizeLogin()function
    {
        String currentpassword=changePasswordBean.getCurrentpassword();
        String newpassword=changePasswordBean.getNewpassword();
        String username=changePasswordBean.getUsername();
        
        
        String dbusername="";  //create two variable for use next process
        String dbpassword="";
        
        String url="jdbc:mysql://localhost:3306/db_mvclogin"; //database connection url string
        String uname="root"; //database username
        String pass=""; //database password
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver"); //load driver
            Connection con=DriverManager.getConnection(url,uname,pass); //create connection
            
            PreparedStatement pstmt=null; //create statement
            
            pstmt=con.prepareStatement("select * from user where username=? and password=? and active=1"); //sql select query 
            pstmt.setString(1,username);
            pstmt.setString(2,currentpassword);
            ResultSet rs=pstmt.executeQuery(); //execute query and set in Resultset object rs.
             
            while(rs.next())
            {    
                dbusername=rs.getString("username");   //fetchable database record username and password store in this two variable dbusername,dbpassword above created 
                dbpassword=rs.getString("password"); 
                
                PreparedStatement pstmt1 = null;
    			pstmt1 = con.prepareStatement("update user set password='"+newpassword+"' where username='"+username+"' and active='1'");
    			int i=pstmt1.executeUpdate();
                
                if(i==1)  //apply if condition check for fetchable database username and password are match for user input side type in textbox
                {
                    return "SUCCESS"; //if valid condition return string "SUCCESS LOGIN" 
                }
                return "error"; 
            } 
           
            pstmt.close(); //close statement
            
            con.close(); //close connection
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return "Password not changed"; //if invalid condition return string "WRONG USERNAME AND PASSWORD"
    }
}
