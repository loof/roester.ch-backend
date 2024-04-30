package ch.roester.app_user;

import ch.roester.FailedValidationException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    public AppUser create(AppUser appUser) throws MessagingException, UnsupportedEncodingException {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        String randomCode = RandomStringUtils.random(64, true, true);
        appUser.setVerificationCode(randomCode);
        appUser.setEnabled(false);
        sendVerificationEmail(appUser);
        return appUserRepository.save(appUser);
    }

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    public AppUser findById(Integer id) {
        return appUserRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public AppUser update(AppUser changing, Integer id) {
        AppUser existing = this.findById(id);
        mergeAppUsers(existing, changing);
        return appUserRepository.save(existing);
    }

    public void deleteById(Integer id) {
        appUserRepository.deleteById(id);
    }

    public boolean verify(String verificationCode) {
        AppUser appUser = appUserRepository.findByVerificationCode(verificationCode);

        if (appUser == null || appUser.isEnabled()) {
            return false;
        } else {
            appUser.setVerificationCode(null);
            appUser.setEnabled(true);
            appUserRepository.save(appUser);

            return true;
        }

    }

    private void sendVerificationEmail(AppUser appUser) throws MessagingException {
        String toAddress = appUser.getEmail();
        String fromAddress = "info@roester.ch";
        String senderName = "röster.ch";
        String subject = "Please verify your registration";

        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(fromAddress));
        message.setRecipients(MimeMessage.RecipientType.TO, toAddress);
        message.setSubject(subject);
        String content = "Hello,<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "röster.ch.";
        String verifyURL =  "http://localhost:8080/auth/verify?code=" + appUser.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        message.setContent(content, "text/html; charset=utf-8");

        mailSender.send(message);

    }

    private void mergeAppUsers(AppUser existing, AppUser changing) {
        Map<String, List<String>> errors = new HashMap<>();

        if (changing.getEmail() != null) {
            if (StringUtils.isNotBlank(changing.getEmail())) {
                existing.setEmail(changing.getEmail());
            } else {
                errors.put("username", List.of("Username must not be empty."));
            }
        }

        if (changing.getPassword() != null) {
            if (StringUtils.isNotBlank(changing.getPassword())) {
                String newPassword = passwordEncoder.encode(changing.getPassword());
                existing.setPassword(newPassword);
            } else {
                errors.put("password", List.of("Password must not be empty."));
            }
        }

        if (!errors.isEmpty()) {
            throw new FailedValidationException(errors);
        }
    }
}