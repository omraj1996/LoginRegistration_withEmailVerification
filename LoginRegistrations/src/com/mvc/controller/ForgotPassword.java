package com.mvc.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.bean.PasswordBean;
import com.mvc.dao.PasswordDao;


public class ForgotPassword extends HttpServlet{

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
		if(request.getParameter("forgotPassword")!=null) //check button click event not null from register.jsp page button
        {
            String mail=request.getParameter("mail");
            PasswordBean passwordBean=new PasswordBean();
            passwordBean.setMail(mail);
            
            PasswordDao passwordDao = new PasswordDao();
            
            String mailValidate=passwordDao.authorizeMail(passwordBean); //send registerBean object values into authorizeRegister() function in RegisterDao class

            if(mailValidate.equals("PASSWORD SENT")) //check calling authorizeRegister() function receive "SUCCESS REGISTER" string message after redirect to index.jsp page
            {
                request.setAttribute("RegiseterSuccessMsg",mailValidate); //apply register successfully message "RegiseterSuccessMsg"
                RequestDispatcher rd=request.getRequestDispatcher("forgotPassword.jsp"); //redirect to index.jsp page
                rd.forward(request, response);
                //Mailer.send("omraj.nitrr@gmail.com","9234649389",username,"hello javatpoint","How r u?"); 
                
            }
            else
            {
                request.setAttribute("RegisterErrorMsg",mailValidate); // apply register error message "RegiseterErrorMsg"
                RequestDispatcher rd=request.getRequestDispatcher("forgotPassword.jsp"); //show error same page register.jsp page
                rd.include(request, response);
            }
        }
    }
}
