package bookmarks.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bookmarks.auth.PropertySourceImpl;
import bookmarks.controller.request.user.RegistrationRequest;
import bookmarks.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthenticationController {
    private static final String CHECK_AUTHENTICATION_MAPPING = "user/authenticated";
    private static final String REGISTRATION_MAPPING = "user/register";

    private final AuthenticationService authenticationService;

    @GetMapping(CHECK_AUTHENTICATION_MAPPING)
    public void checkAuthentication(@CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId) {
        log.info("{} is actually logged in.", userId);
    }

    @PostMapping(REGISTRATION_MAPPING)
    public void register(@RequestBody @Valid RegistrationRequest request) {
        log.info("Registraton endpoint called with userName {}", request.getUserName());
        authenticationService.register(request.getUserName(), request.getPassword());
    }
}
