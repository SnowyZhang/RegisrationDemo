package com.snowy.emailVerification.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.snowy.emailVerification.registration.RegistrationService;

@RestController
@RequestMapping( "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

//    @PostMapping("/confirm/{token}")
//    public String confirm(@PathVariable String token) {
//        return registrationService.confirmToken(token);
//    }
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
