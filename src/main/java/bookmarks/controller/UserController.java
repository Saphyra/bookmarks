package bookmarks.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bookmarks.auth.PropertySourceImpl;
import bookmarks.controller.request.OneStringParamRequest;
import bookmarks.controller.request.user.AccountDeleteRequest;
import bookmarks.controller.request.user.ChangePasswordRequest;
import bookmarks.controller.request.user.ChangeUserNameRequest;
import bookmarks.controller.request.user.RegistrationRequest;
import bookmarks.service.UserDeleteService;
import bookmarks.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private static final String CHECK_AUTHENTICATION_MAPPING = "user/authenticated";
    private static final String REGISTRATION_MAPPING = "user/register";
    private static final String CHANGE_PASSWORD_MAPPING = "user/password";
    private static final String CHANGE_USER_NAME_MAPPING = "user/name";
    private static final String DELETE_ACCOUNT_MAPPING = "user";
    private static final String USER_NAME_EXISTS_MAPPING = "user/name/exist";

    private final UserService userService;
    private final UserDeleteService userDeleteService;

    @PostMapping(CHANGE_PASSWORD_MAPPING)
    public void changePassword(
        @RequestBody @Valid ChangePasswordRequest request,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
        ){
        log.info("{} wants to chanhe his password", userId);
        userService.changePassword(request, userId);
    }

    @PostMapping(CHANGE_USER_NAME_MAPPING)
    public void changeUserName(
        @RequestBody @Valid ChangeUserNameRequest request,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to change his userName", userId);
        userService.changeUserName(request, userId);
    }

    @DeleteMapping(DELETE_ACCOUNT_MAPPING)
    public void deleteAccount(
        @RequestBody @Valid AccountDeleteRequest request,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to delete his account", userId);
        userDeleteService.deleteAccount(request, userId);
    }

    @PostMapping(USER_NAME_EXISTS_MAPPING)
    public boolean isUserNameExists(@RequestBody @Valid OneStringParamRequest request){
        log.info("Someone wants to know if userName {} is exists", request.getValue());
        return userService.isUserNameExists(request.getValue());
    }

    @GetMapping(CHECK_AUTHENTICATION_MAPPING)
    public void checkAuthentication(@CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId) {
        log.info("{} is actually logged in.", userId);
    }

    @PostMapping(REGISTRATION_MAPPING)
    public void register(@RequestBody @Valid RegistrationRequest request) {
        log.info("Registraton endpoint called with userName {}", request.getUserName());
        userService.register(request.getUserName(), request.getPassword());
    }
}
