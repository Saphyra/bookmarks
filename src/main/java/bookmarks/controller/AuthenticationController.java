package bookmarks.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bookmarks.service.AuthenticationService;
import bookmarks.controller.request.LoginRequest;
import bookmarks.controller.request.RegistrationRequest;
import bookmarks.filter.FilterHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthenticationController {
    private static final String LOGIN_MAPPING = "login";
    private static final String LOGOUT_MAPPING = "logout";
    private static final String REGISTRATION_MAPPING = "user/register";

    private final AuthenticationService authenticationService;

    @PostMapping(LOGIN_MAPPING)
    public void login(@RequestBody @Valid LoginRequest request) {

    }

    @GetMapping(LOGOUT_MAPPING)
    public void logout(
        @CookieValue(FilterHelper.COOKIE_ACCESS_TOKEN) String accessToken,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ) {

    }

    @PostMapping(REGISTRATION_MAPPING)
    public void register(@RequestBody @Valid RegistrationRequest request) {
        log.info("Registraton endpoint called with userName {}", request.getUserName());
        authenticationService.register(request.getUserName(), request.getPassword());
    }
}
