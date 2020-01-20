package com.mvc.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mvc.bean.ChangePasswordBean;
import com.mvc.dao.ChangePasswordDao;

public class ChangePasswordController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        if(request.getParameter("chng_pass")!=null) //check button click event not null from login.jsp page button
        {
            String password=request.getParameter("current"); //get textbox name "txt_username"
            String newPassword=request.getParameter("new"); //get textbox name "txt_password"
            
            
            mdjavahash md = new mdjavahash();

			String newpassword = "";
			try {
				newpassword = md.getHashPass(newPassword);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String currentpassword = "";
			try {
				currentpassword = md.getHashPass(password);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpSession session=request.getSession(true);
			String username=(String) session.getAttribute("loginUser");
            ChangePasswordBean changePasswordBean=new ChangePasswordBean(); //this class contain seeting up all received values from index.jsp page to setter and getter method for application require effectively 
            
            changePasswordBean.setCurrentpassword(currentpassword); //set username through loginBean object
            changePasswordBean.setNewpassword(newpassword); //set password through loginBean object
            changePasswordBean.setUsername(username);
            
            
            ChangePasswordDao chnagePasswordDao=new ChangePasswordDao(); //this class contain main logic to perform function calling and database operation
            
            String authorize=chnagePasswordDao.authorizeLogin(changePasswordBean); //send loginBean object values into authorizeLogin() function in LoginDao class
            
            if(authorize.equals("SUCCESS")) //check calling authorizeLogin() function receive string "SUCCESS LOGIN" message after continue process
            {
                //HttpSession session=request.getSession(true); //session is created
               // session.setAttribute("loginUser",username); //session name is "login" and  store username in "getUsername()" get through loginBean object
                //session.setAttribute("loginPass",loginBean.getPassword());
                RequestDispatcher rd=request.getRequestDispatcher("logout.jsp"); //redirect to welcome.jsp page
                rd.forward(request, response);
            }
            else
            {
                request.setAttribute("WrongLoginMsg",authorize); //wrong login error message is "WrongLoginMsg"
                response.sendRedirect("index.jsp");
               
            }
        }
    }


}
