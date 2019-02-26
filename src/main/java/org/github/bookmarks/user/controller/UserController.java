package org.github.bookmarks.user.controller;

import javax.validation.Valid;

import org.github.bookmarks.auth.PropertySourceImpl;
import org.github.bookmarks.user.UserFacade;
import org.github.bookmarks.user.controller.request.AccountDeleteRequest;
import org.github.bookmarks.user.controller.request.ChangePasswordRequest;
import org.github.bookmarks.user.controller.request.ChangeUserNameRequest;
import org.github.bookmarks.user.controller.request.RegistrationRequest;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.github.bookmarks.common.controller.request.OneStringParamRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private static final String CHECK_AUTHENTICATION_MAPPING = "domain/authenticated";
    private static final String REGISTRATION_MAPPING = "domain/register";
    private static final String CHANGE_PASSWORD_MAPPING = "domain/password";
    private static final String CHANGE_USER_NAME_MAPPING = "domain/name";
    private static final String DELETE_ACCOUNT_MAPPING = "domain";
    private static final String USER_NAME_EXISTS_MAPPING = "domain/name/exist";

    private final UserFacade userFacade;

    @PostMapping(CHANGE_PASSWORD_MAPPING)
    public void changePassword(
        @RequestBody @Valid ChangePasswordRequest request,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
        ){
        log.info("{} wants to chanhe his password", userId);
        userFacade.changePassword(request, userId);
    }

    @PostMapping(CHANGE_USER_NAME_MAPPING)
    public void changeUserName(
        @RequestBody @Valid ChangeUserNameRequest request,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to change his userName", userId);
        userFacade.changeUserName(request, userId);
    }

    @DeleteMapping(DELETE_ACCOUNT_MAPPING)
    public void deleteAccount(
        @RequestBody @Valid AccountDeleteRequest request,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to delete his account", userId);
        userFacade.deleteAccount(request, userId);
    }

    @PostMapping(USER_NAME_EXISTS_MAPPING)
    public boolean isUserNameExists(@RequestBody @Valid OneStringParamRequest request){
        log.info("Someone wants to know if userName {} is exists", request.getValue());
        return userFacade.isUserNameExists(request.getValue());
    }

    @GetMapping(CHECK_AUTHENTICATION_MAPPING)
    public void checkAuthentication(@CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId) {
        log.info("{} is actually logged in.", userId);
    }

    @PostMapping(REGISTRATION_MAPPING)
    public void register(@RequestBody @Valid RegistrationRequest request) {
        log.info("Registraton endpoint called with userName {}", request.getUserName());
        userFacade.register(request.getUserName(), request.getPassword());
    }
}
