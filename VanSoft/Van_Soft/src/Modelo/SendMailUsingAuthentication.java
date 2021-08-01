/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;


/**
 *
 * @author Gonga
 */
public class SendMailUsingAuthentication {

    private static final String SMTP_HOST_NAME = "localhost"; //or simply "localhost" gemini.jvmhost.com
    private static final String SMTP_AUTH_USER = "Gonga";
    private static final String SMTP_AUTH_PWD = "Zegonga";
    private static final String emailMsgTxt = "Body";
    private static final String emailSubjectTxt = "Subject";
    private static final String emailFromAddress = "vaniked100596@gmail.com";

//    // Add Email address to who email needs to be sent to
//    private static final String[] emailList = {"he@domain.net"};

    public void postMail(String recipients[], String subject, String message, String from) throws MessagingException, AuthenticationFailedException {
        boolean debug = true;

//Set the host smtp address
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "FALSE");
        props.put("mail.smtp.port", "25");
        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getDefaultInstance(props, auth);

        session.setDebug(debug);

// create a message
        Message msg = new MimeMessage(session);

// set the from and to address
        InternetAddress addressFrom = new InternetAddress(emailFromAddress);
        msg.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo
        );

// Setting the Subject and Content Type
        msg.setSubject(subject);
        msg.setContent(message, "text/plain");
        Transport.send(msg);

    }

    private static class SMTPAuthenticator extends Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }

}
