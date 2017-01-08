package com.aring.service.impl;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.stereotype.Service;

import com.aring.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	
	private static ExecutorService executorService = Executors.newFixedThreadPool(10);

	public void send(String receEmail,String title,String context) throws Exception{
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try{
					Properties properties = new Properties();
					properties.put("mail.smtp.auth", "true");
					properties.put("mail.smtp.host", "smtp.2980.com");
					properties.put("mail.transport.protocol", "SMTP");
					Session session = Session.getInstance(properties,authenticator);
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(SENDER_ADDRESS));
					message.setRecipient(RecipientType.TO, new InternetAddress(receEmail));	
					message.setSubject(title);
			        message.setSentDate(new Date());
			        message.setContent(context,"text/html;charset=utf-8");
			        Transport.send(message);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		executorService.execute(task);
	}
	
	private	static Authenticator authenticator = new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {		
			return new PasswordAuthentication(SENDER_ADDRESS, SENDER_PASS);
		}
	};	
	
	public static void main(String[] args) throws AddressException, MessagingException {
		/*String context = EMAIL_CONTEXT1+"test"+EMAIL_CONTEXT2;
		EmailServiceImpl.send("aringlai@163.com", EMAIL_TITLE, context);*/
	}
	
	
}
