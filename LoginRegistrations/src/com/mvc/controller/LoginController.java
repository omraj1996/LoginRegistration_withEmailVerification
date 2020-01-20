package com.mvc.controller;

import com.mvc.bean.LoginBean;
import com.mvc.dao.LoginDao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

public class LoginController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        if(request.getParameter("btn_login")!=null) //check button click event not null from login.jsp page button
        {
            String username=request.getParameter("txt_username"); //get textbox name "txt_username"
            String password=request.getParameter("txt_password"); //get textbox name "txt_password"
            
            mdjavahash md = new mdjavahash();

			String newpassword = "";
			try {
				newpassword = md.getHashPass(password);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            if (username != null && username.trim().length() > 0 && newpassword != null && password.trim().length() > 0) {
    			System.out.println(username + ":" + newpassword);
    			if (username != null && username.length() != 0 && username.equals(request.getParameter("txt_username")) && password != null
    					&& password.length() != 0 && password.equals(request.getParameter("txt_password"))) {
    				if (request.getParameter("remember") != null) {
    					String remember = request.getParameter("remember");
    					System.out.println("remember : " + remember);
    					Cookie cUserName = new Cookie("cookuser", username.trim());
    					Cookie cPassword = new Cookie("cookpass", password.trim());
    					Cookie cRemember = new Cookie("cookrem", remember.trim());
    					cUserName.setMaxAge(60 * 60 * 24 * 15);// 15 days
    					cPassword.setMaxAge(60 * 60 * 24 * 15);
    					cRemember.setMaxAge(60 * 60 * 24 * 15);
    					response.addCookie(cUserName);
    					response.addCookie(cPassword);
    					response.addCookie(cRemember);
    				}
    			}
            }
            
            LoginBean loginBean=new LoginBean(); //this class contain seeting up all received values from index.jsp page to setter and getter method for application require effectively 
            
            loginBean.setUsername(username); //set username through loginBean object
            loginBean.setPassword(newpassword); //set password through loginBean object
            //loginBean.setNewpassword(newpassword);
            
            LoginDao loginDao=new LoginDao(); //this class contain main logic to perform function calling and database operation
            
            String authorize=loginDao.authorizeLogin(loginBean); //send loginBean object values into authorizeLogin() function in LoginDao class
            
            if(authorize.equals("SUCCESS LOGIN")) //check calling authorizeLogin() function receive string "SUCCESS LOGIN" message after continue process
            {
                HttpSession session=request.getSession(true); //session is created
                session.setAttribute("loginUser",username); //session name is "login" and  store username in "getUsername()" get through loginBean object
                //session.setAttribute("loginPass",loginBean.getPassword());
                RequestDispatcher rd=request.getRequestDispatcher("welcome.jsp"); //redirect to welcome.jsp page
                rd.forward(request, response);
            }
            else
            {
                request.setAttribute("WrongLoginMsg",authorize); //wrong login error message is "WrongLoginMsg"
                response.sendRedirect("index.jsp");
                //RequestDispatcher rd=request.getRequestDispatcher("index.jsp"); //show error same index.jsp page
               // rd.include(request, response);
            }
        }
    }

}