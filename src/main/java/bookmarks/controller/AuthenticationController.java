package bookmarks.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bookmarks.domain.accesstoken.AccessToken;
import bookmarks.service.AuthenticationService;
import bookmarks.controller.request.LoginRequest;
import bookmarks.controller.request.RegistrationRequest;
import bookmarks.filter.FilterHelper;
import bookmarks.util.CookieUtil;
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
    private final CookieUtil cookieUtil;

    @PostMapping(LOGIN_MAPPING)
    public void login(
        HttpServletResponse response,
        @RequestBody @Valid LoginRequest request
    ) {
        log.info("Login endpoint called with userName {}", request.getUserName());
        AccessToken accessToken = authenticationService.login(request.getUserName(), request.getPassword(), request.getRemember());
        int expiration = accessToken.getPersistent() ? Integer.MAX_VALUE : -1;
        log.debug("Cookie expiration: {}", expiration);
        cookieUtil.setCookie(response, FilterHelper.COOKIE_ACCESS_TOKEN, accessToken.getAccessTokenId(), expiration);
        cookieUtil.setCookie(response, FilterHelper.COOKIE_USER_ID, accessToken.getUserId(), expiration);
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
