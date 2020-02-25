package com.hackathon.alert.twitter.email;


import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;


public class MailSender {

    private static final String SMTP_SERVER = "localhost";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    private static final String EMAIL_FROM = "alerts@elsevier.com";
    private static final String EMAIL_TO = "john.smith@gmail.com";
    private static final String EMAIL_TO_CC = "";

    private static final String EMAIL_SUBJECT = "Trend topics for you";

    public static void sendMail(Map<String, List<String>> publicationsForTT) {

        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "25"); // default port 25

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            // from
            msg.setFrom(new InternetAddress(EMAIL_FROM));

            // to
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(EMAIL_TO, false));

            // cc
            msg.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(EMAIL_TO_CC, false));

            // subject
            msg.setSubject(EMAIL_SUBJECT);



            msg.setText(generateEmailBody(publicationsForTT));

            msg.setSentDate(new Date());

            // Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    private static String generateEmailBody(Map<String, List<String>> publicationsForTT) {
        String emailText = "Dear researcher, \r\n";
        emailText += "Here are some publications for you for trending topics \r\n";
        String publicationLinks = "";
        for (Map.Entry<String,List<String>> entry : publicationsForTT.entrySet()){
            publicationLinks +=  entry.getKey() + " looks like trending right now. Here are publications with this topic: \r\n";
            String links = "";
            for(Iterator i = entry.getValue().iterator();i.hasNext();){
                links += i.next().toString();
            }
            publicationLinks += links;
        }
        emailText += publicationLinks;

        return  emailText;
    }
}