package services;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Service class for sending emails, including verification and password reset
 * emails.
 * Uses JavaMail API for SMTP communication.
 */
public class EmailService {

    private static final String FROM_EMAIL = "cst8319@epicwuxia.com"; // Your email address
    private static final String EMAIL_PASSWORD = "5xi%Yfmw"; // Your email password
    private static final String SMTP_HOST = "smtp.zoho.com"; // SMTP host for Zoho
    private static final int SMTP_PORT = 465; // SMTP port for SSL

    /**
     * Creates and returns a configured {@link Session} object for email sending.
     *
     * @return a {@link Session} instance with SMTP settings
     */
    private static Session getSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true"); // Enable SSL
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.ssl.trust", SMTP_HOST); // Trust the SMTP host

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, EMAIL_PASSWORD);
            }
        });
    }

    /**
     * Sends a verification email with a given verification code.
     *
     * @param toEmail          the recipient's email address
     * @param verificationCode the verification code to include in the email
     */
    public static void sendVerificationEmail(String toEmail, String verificationCode) {
        System.out.println("Sending verification email to: " + toEmail);
        System.out.println("Verification code: " + verificationCode);

        if (toEmail == null || toEmail.isEmpty()) {
            System.out.println("Error: toEmail is null or empty.");
            return;
        }

        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Email Verification");
            message.setText("Your verification code is: " + verificationCode);

            Transport.send(message);
            System.out.println("Verification email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a password reset email with a given reset code.
     *
     * @param toEmail   the recipient's email address
     * @param resetCode the password reset code to include in the email
     */
    public static void sendResetPasswordEmail(String toEmail, String resetCode) {
        System.out.println("Sending reset password email to: " + toEmail);
        System.out.println("Reset code: " + resetCode);

        if (toEmail == null || toEmail.isEmpty()) {
            System.out.println("Error: toEmail is null or empty.");
            return;
        }

        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Password Reset");
            message.setText("Your password reset code is: " + resetCode);

            Transport.send(message);
            System.out.println("Reset password email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
