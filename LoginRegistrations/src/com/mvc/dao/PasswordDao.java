package com.mvc.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.mvc.bean.PasswordBean;


public class PasswordDao {
	public String authorizeMail(PasswordBean passwordBean) // create authorizeRegister()function
	{
		String mail= passwordBean.getMail();

				Properties props = new Properties();   
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "465");
				props.put("mail.transport.protocol", "smtp");
				props.put("mail.smtp.user", "omraj.nitrr@gmail.com");
				props.put("mail.smtp.auth", "true");		
				props.put("mail.smtp.starttls.enable","true");
				props.put("mail.smtp.debug", "false");
				props.put("mail.smtp.socketFactory.port", "465");    
				props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");    
				props.put("mail.smtp.socketFactory.fallback", "false");
				
				
				try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db_mvclogin","root","");
					String query="select password from user where username=?";
					PreparedStatement statmnt = con.prepareStatement(query);
					statmnt.setString(1, mail);
					ResultSet result=statmnt.executeQuery();
					if(result.next()){		
							
								//String fetchedPassword=dbpassword;
								String fetchedPassword=result.getString("password");
								Session session = Session.getDefaultInstance(props, null);
								session.setDebug(true);
								MimeMessage message = new MimeMessage(session);
								message.setText("Your password is "+fetchedPassword);
								message.setSubject("Password for your account");
								message.setFrom(new InternetAddress("omraj.nitrr@gmail.com"));
								message.addRecipient(RecipientType.TO, new InternetAddress(mail.trim()));
								message.saveChanges();
								Transport transport = session.getTransport("smtp");
								transport.connect("smtp.gmail.com","omraj.nitrr@gmail.com","9234649389");
								transport.sendMessage(message,message.getAllRecipients());
								transport.close();
								System.out.println("You password is mailed to you");
							}
				}
					catch(Exception e){
					e.printStackTrace();
					System.out.println("Email Address not found");
					}
				//System.out.println(dbpassword);
					return "PASSWORD SENT"; // if valid condition return string "SUCCESS LOGIN"

		//return "Enter Correct Mail";
	}
}
