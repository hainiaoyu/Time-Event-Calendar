/*
 * Send Email to the user reminding the added event
 */
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class sendMail {
	private static String SMPT_HOSTNAME; //Simple Mail Transfer Protocol 
	private static String USERNAME;
	private static String PASSWORD;
	private Properties props; // properties class read configuration file
	private MimeMessage msg; // MIME format message (Multipurpose Internet Mail Extensions)
	
	public sendMail() { // set smtp protocol, username and password
		SMPT_HOSTNAME = "smtp.163.com";
		USERNAME = "haoyuapply@163.com";
		PASSWORD = "xmh123456";
	}
	
	//send to whom and authorirization
	public void sendEventMail(String emailAddress, String emailEvent) {
		props = new Properties();
	    props.put("mail.smtp.host", SMPT_HOSTNAME); // propoties heritage from hash map so can use put
	    props.put("mail.from","haoyuapply@163.com");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.debug", "true");
	
	    // new session object used in web
	    Session session = Session.getInstance(props, new Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(USERNAME, PASSWORD);
	        }
	    });
	    
	    try {
	        msg = new MimeMessage(session);
	        msg.setFrom();
	        msg.setRecipients(Message.RecipientType.TO,
	        		emailAddress);
	        msg.setSubject("810_calendar_reminder");
	        msg.setSentDate(new Date());
	        msg.setText(emailEvent);
	        Transport.send(msg);
	     } catch (MessagingException e) {
	        System.out.println("send failed, exception: " + e);
	     }
	}
}
