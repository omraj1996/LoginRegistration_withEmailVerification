package com.mvc.controller;
import java.util.Properties;    
import javax.mail.*;    
import javax.mail.internet.*;   
public class Mailer {
	private String userEmail;
	private String myHash;
	
	public Mailer(String userEmail, String myHash) {
		this.userEmail = userEmail;
		this.myHash = myHash;
	}

	public void sendMail(){
        //Get properties object    
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
        //get Session   
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);    
        //compose message    
        try {    
         MimeMessage message = new MimeMessage(session); 
         message.setFrom(new InternetAddress("omraj.nitrr@gmail.com"));
         message.addRecipient(Message.RecipientType.TO,new InternetAddress(userEmail));    
         message.setSubject("Email Verification");    
         message.setText("Verification Link...");
         message.setText("Your Verification Link :: "+"http://localhost:8585/LoginRegistrations/ActivateAccount?key1="+userEmail+"&key2="+myHash);
         message.saveChanges();
         //send message  
         Transport transport = session.getTransport("smtp");
         transport.connect("smtp.gmail.com","omraj.nitrr@gmail.com","9234649389");
         transport.sendMessage(message,message.getAllRecipients());
         transport.close();    
         System.out.println("message sent successfully");    
        } //catch (MessagingException e) {throw new RuntimeException(e);}
        catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in Sending Mail.");
		}
           
  }  

}
