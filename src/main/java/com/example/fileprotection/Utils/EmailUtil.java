package com.example.fileprotection.Utils;

import com.example.fileprotection.Config.MailConfig;
import javafx.scene.control.Alert;
import org.hibernate.SessionFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailUtil {

    private final static Session session;
    static{
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", MailConfig.HOST_NAME);
//        props.put("mail.smtp.socketFactory.port", MailConfig.SSL_PORT);
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", MailConfig.TSL_PORT);
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.ssl.trust","*");

        // get Session
        session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailConfig.APP_EMAIL, MailConfig.APP_PASSWORD);
            }
        });

    }
    public static void sendEmail(String subject, String body){
        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(MailConfig.RECEIVE_EMAIL));
            message.setSubject(subject);
            message.setText(body);

            // send message
            Transport.send(message);

            DialogUtil.showAlert("Success","Email send successfully!", Alert.AlertType.INFORMATION);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
