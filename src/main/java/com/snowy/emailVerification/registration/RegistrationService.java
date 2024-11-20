package com.snowy.emailVerification.registration;

import com.snowy.emailVerification.appUser.AppUser;
import com.snowy.emailVerification.appUser.AppUserRepository;
import com.snowy.emailVerification.appUser.AppUserRole;
import com.snowy.emailVerification.appUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    public String register(RegistrationRequest request) {
        boolean isEmailValid = emailValidator.test(request.getEmail());

        if (!isEmailValid) {
            throw new IllegalStateException("邮箱不合法");
        }else{
            System.out.println("邮箱合法");
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}
