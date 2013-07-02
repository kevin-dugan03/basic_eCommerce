package Models.StoreModels;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Email class that generates confirmation emails when a user registers
 * and places orders. 
 * @author KDugan
 */
public class Email {
	
	private Customer customer;
	private Properties props;
	private Session mailSession;
	private String to, from, subject, body;
	private Message msg;
	
	public Email(Customer customer) {
		this.customer = customer;
	}
	
	/**
	 * Sends a confirmation email when a user successfully registers.
	 * @throws MessagingException
	 */
	public void sendRegisterConfirm() throws MessagingException
	{
		//create the message
		to = customer.getEmail();
		from = "portablecomputing@yahoo.com";
		subject = "Portable Computers, LLC Confirmation Email";
		body = "Thank you for registering with Portable Computers, LLC. Your " +
			"Username is '" + customer.getUsername() + "' and your password is '" + 
			customer.getPassword() + "'. Have a nice day.\n\nJoe Manager \nPortable Computers, LLC";
		
		buildMailSession();
		buildMessage();
		sendMessage();
	}
	
	/**
	 * Sends a confirmation email after a user successfully places an order.
	 * @throws MessagingException
	 */
	public void sendOrderConfirm() throws MessagingException {
		
		//create the message
		to = customer.getEmail();
		from = "portablecomputing@yahoo.com";
		subject = "Portable Computers, LLC Confirmation Email";
		body = "Thank you for placing your order with Portable Computers, LLC. You can " +
			"check the status of your order by visiting http://localhost:8080/COMP461 and " +
			"logging in to view your order history.\nHave a nice day.\n\nJoe Manager\nPortable Computers, LLC";
		
		buildMailSession();
		buildMessage();
		sendMessage();

	}
	
	/**
	 * Builds the mail session.
	 */
	public void buildMailSession() {
		//Get a mail session
		props = System.getProperties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", "smtp.mail.yahoo.com");
		props.put("mail.smtps.port", 465);
		props.put("mail.smtps.auth", "true");
		mailSession = Session.getDefaultInstance(props);
		mailSession.setDebug(true);
	}
	
	/**
	 * Builds a message object.
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void buildMessage() throws AddressException, MessagingException {
		
		msg = new MimeMessage(mailSession);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject(subject);
		msg.setText(body);
	}
	
	/**
	 * Sends the message.
	 * @throws MessagingException
	 */
	public void sendMessage() throws MessagingException {
		Transport transport = mailSession.getTransport();
		transport.connect("portablecomputing", "123testing");
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}
}
