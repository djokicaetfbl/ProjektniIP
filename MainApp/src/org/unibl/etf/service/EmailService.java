package org.unibl.etf.service;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import org.unibl.etf.dao.UserDAO;
import org.unibl.etf.dto.Post;
import org.unibl.etf.dto.User;

public class EmailService {
	private static final String USERNAME = "djoleipetf";
	private static final  String PASSWORD = "d2G6QPLfhKeNCkM";
	
	
    public static void sendMails(Post post) {
        String from = USERNAME;
        String pass = PASSWORD;
        ArrayList<User> users=UserDAO.getUsersForEmergencyNotification();
        
        String[] to=new String[users.size()];
        for(int i=0; i<users.size(); i++) {
        	to[i]=users.get(i).getEmail();
        }
        String subject = "Emergency notification";
        String body = post.getContents();
        
        if(post.getGeographicLatitude() != 0 && post.getGeographicLongitude() != 0) {
        	body += "Geographic latitude: "+post.getGeographicLatitude() + " Geographic longitude: "+post.getGeographicLongitude();
        }       

        sendFromGMail(from, pass, to, subject, body);
    }
	
    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");


        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // sa steka


        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
