package org.tim16.booker.service;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import org.tim16.booker.model.admins.AirlineAdmin;
import org.tim16.booker.model.admins.HotelAdmin;
import org.tim16.booker.model.admins.RentACarAdmin;
import org.tim16.booker.model.admins.SysAdmin;
import org.tim16.booker.model.users.RegisteredUser;
import org.tim16.booker.model.users.User;
import org.tim16.booker.model.utility.VerificationToken;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Autowired
    private VerificationTokenService service;

    @Async
    public void sendActivationLink(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        service.create(verificationToken);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(env.getProperty("spring.mail.username"));
        message.setSubject("Activation link for ISA-MRS Tours.");
        String content;

        if (user instanceof RegisteredUser) {
            RegisteredUser registeredUser = (RegisteredUser)user;
            message.setTo(registeredUser.getEmail());
            try {
                content = String.format(
                        "Hello " + registeredUser.getName() + "!\nTo finish your registration, click on link below:\nhttp://localhost:8080/login/%s",
                        URLEncoder.encode(token, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return;
            }
        } else if (user instanceof AirlineAdmin) {
            AirlineAdmin admin = (AirlineAdmin) user;
            message.setTo((admin.getEmail()));
            try {
                content = String.format(
                        "Hello " + admin.getName() + "!\nTo finish your registration, click on link below and sign in:\nhttp://localhost:8080/login/%s" +
                                "\n\tusername: " + admin.getUsername() + "\n\tpassword: 123",
                        URLEncoder.encode(token, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return;
            }
        } else if (user instanceof HotelAdmin) {
            HotelAdmin admin = (HotelAdmin) user;
            message.setTo((admin.getEmail()));
            try {
                content = String.format(
                        "Hello " + admin.getName() + "!\nTo finish your registration, click on link below and sign in:\nhttp://localhost:8080/login/%s" +
                                "\n\tusername: " + admin.getUsername() + "\n\tpassword: 123",
                        URLEncoder.encode(token, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return;
            }
        } else if (user instanceof RentACarAdmin) {
            RentACarAdmin admin = (RentACarAdmin) user;
            message.setTo((admin.getEmail()));
            try {
                content = String.format(
                        "Hello " + admin.getName() + "!\nTo finish your registration, click on link below and sign in:\nhttp://localhost:8080/login/%s" +
                                "\n\tusername: " + admin.getUsername() + "\n\tpassword: 123",
                        URLEncoder.encode(token, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return;
            }
        } else {
            SysAdmin admin = (SysAdmin) user;
            message.setTo((admin.getEmail()));
            try {
                content = String.format(
                        "Hello " + admin.getName() + "!\nTo finish your registration, click on link below and sign in:\nhttp://localhost:8080/login/%s" +
                                "\n\tusername: " + admin.getUsername() + "\n\tpassword: 123",
                        URLEncoder.encode(token, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return;
            }
        }

        message.setText(content);
        mailSender.send(message);
    }
}
