package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Properties;
//
//public class EmailSender {
//    public static void main(String[] args) {
//
//        String host="mail.smtp.host";
//        final String user= Const.EMAIL;//change accordingly
//        final String password=Const.PASSWORD;//change accordingly
//
//        String to="quangtuanb3@gmail.com";//change accordingly
//
//        //Get the session object
//        Properties props = new Properties();
//        props.put("mail.smtp.host",host);
//        props.put("mail.smtp.auth", "true");
//
//
//        Session session = Session.getDefaultInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(user,password);
//                    }
//                });
//
//        //Compose the message
//        try {
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(user));
//            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
//            message.setSubject("javatpoint");
//            message.setText("This is simple program of sending email using JavaMail API");
//
//            //send the message
//            Transport.send(message);
//
//            System.out.println("message sent successfully...");
//
//        } catch (MessagingException e) {e.printStackTrace();}
//    }
//}
//import java.util.*;
//        import javax.mail.*;
//        import javax.mail.internet.*;
//
public class EmailSender {

    public static void main(String[] args) {

        String to = "quangtuanb3@gmail.com"; // Recipient's email address
        String from = Constant.EMAIL; // Sender's email address
        String host = "smtp.gmail.com"; // SMTP server hostname
        String username = Constant.EMAIL; // Sender's email username
        String password = Constant.PASSWORD; // Sender's email password

        // Set up properties for the email session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Create a session with authentication
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a new message
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set the email subject and body
            message.setSubject("Test email from Java Mail");
            message.setText("Hello, this is a test email from Java Mail.");

            // Send the email message
            Transport.send(message);

            System.out.println("Email sent successfully.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}