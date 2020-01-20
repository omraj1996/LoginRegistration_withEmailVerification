package com.mvc.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.*;
import java.util.Random;

import com.mvc.bean.RegisterBean;
import com.mvc.dao.RegisterDao;

public class RegisterController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("btn_register") != null) // check button click event not null from register.jsp page
															// button
		{
			String firstname = request.getParameter("txt_firstname");
			String lastname = request.getParameter("txt_lastname");
			String username = request.getParameter("txt_username"); // get all textbox name from register.jsp page
			String password = request.getParameter("txt_password");

			mdjavahash md = new mdjavahash();

			String newpassword = "";
			try {
				newpassword = md.getHashPass(password);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String myHash="";
			Random random = new Random();
			random.nextInt(999999);
			try {
				myHash=md.getHashPass(random.toString());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			RegisterBean registerBean = new RegisterBean(); // this class contain seeting up all received values from
															// register.jsp page to setter and getter method for
															// application require effectively

			registerBean.setFirstname(firstname);
			registerBean.setLastname(lastname);
			registerBean.setUsername(username); // set the all value through registerBean object
			registerBean.setPassword(newpassword);
			registerBean.setMyhash(myHash);

			RegisterDao registerdao = new RegisterDao(); // this class contain main logic to perform function calling
															// and database operation

			String registerValidate = registerdao.authorizeRegister(registerBean); // send registerBean object values
																					// into authorizeRegister() function
																					// in RegisterDao class

			if (registerValidate.equals("SUCCESS")) // check calling authorizeRegister() function receive
																// "SUCCESS REGISTER" string message after redirect to
																// index.jsp page
			{
				request.setAttribute("RegiseterSuccessMsg", registerValidate); // apply register successfully message
																				// "RegiseterSuccessMsg"
				RequestDispatcher rd = request.getRequestDispatcher("verify.jsp"); // redirect to index.jsp page
				rd.forward(request, response);
				// Mailer.send("omraj.nitrr@gmail.com","9234649389",username,"hello
				// javatpoint","How r u?");

			} else {
				request.setAttribute("RegisterErrorMsg", registerValidate); // apply register error message
																			// "RegiseterErrorMsg"
				RequestDispatcher rd = request.getRequestDispatcher("register.jsp"); // show error same page
																						// register.jsp page
				rd.include(request, response);
			}

		}
	}

}