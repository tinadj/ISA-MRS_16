package org.tim16.booker.service;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import org.tim16.booker.model.users.RegisteredUser;
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
    public void sendActiationLink(RegisteredUser user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();

        verificationToken.setToken(token);
        verificationToken.setUser(user);
        service.create(verificationToken);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setFrom(env.getProperty("spring.mail.username"));
        message.setSubject("Activation link for ISA-MRS Tours.");
        String content;

        try {
            content = String.format(
                    "To finish your registration, click on link below:\nhttp://localhost:8081/auth/confirm?token=%s",
                    user.getEmail(), URLEncoder.encode(token, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return;
        }
        message.setText(content);
        mailSender.send(message);
    }
}
