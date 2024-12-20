package com.snowy.emailVerification.registration;

import com.snowy.emailVerification.appUser.AppUser;
import com.snowy.emailVerification.appUser.AppUserRepository;
import com.snowy.emailVerification.appUser.AppUserRole;
import com.snowy.emailVerification.appUser.AppUserService;
import com.snowy.emailVerification.email.EmailSender;
import com.snowy.emailVerification.email.EmailService;
import com.snowy.emailVerification.registration.token.ConfirmationToken;
import com.snowy.emailVerification.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public String register(RegistrationRequest request) {
        boolean isEmailValid = emailValidator.test(request.getEmail());

        if (!isEmailValid) {
            throw new IllegalStateException("邮箱不合法");
        }else{
            System.out.println("邮箱合法");
        }
        String token =appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                ));
        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailSender.send(request.getEmail(),
                "点击链接确认邮箱: " + buildEmail(request.getFirstName(), link));
        return token;

    }

    private String buildEmail(String firstName, String link) {
        //创建一个html格式的邮件
        return "<h1>你好，" + firstName + "</h1>"
                + "<p>请点击下面的链接确认你的邮箱地址:</p>"
                + "<a href=\"" + link + "\">确认邮箱</a>";
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
