package com.hquach.services;

import com.hquach.model.User;
import com.hquach.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Email Service for sending auto email such as create user, reset password, etc...
 * Java Email API is currently used for sending emails.
 * TODO: implement GMail API or Email provider
 */
@Service("emailService")
public class EmailService {
    @Autowired
    UserRepository userRepository;

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    public void sendUserRegistrationEmail(User user, String password) {
        String content = "Your account has been created successfully. \n"
                + "Account detail: \n"
                + "\t Username: " + user.getUserId() + "\n"
                + "\t Password: " + password;
        sendEmail(user.getEmail(), "User Registration", content);
    }
/*

    public void sendResetPasswordEmail(PasswordToken token, String appContext) {
        String content = "Please use the link below to reset your password. \n"
                + appContext + "/users/resetPassword?token=" + token.getToken();
        sendEmail(token.getEmail(), "Reset Password", content);
    }
*/

    private void sendEmail(String toEmail, String subject, String content) {

        final String username = "username";
        final String password = "password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            LOG.info("Email sent to " + toEmail);

        } catch (MessagingException e) {
            LOG.error("Unable to send email to " + toEmail, e);
            throw new RuntimeException(e);
        }
    }
}
