package bookmarks.controller;

import javax.validation.Valid;

import bookmarks.controller.request.user.AccountDeleteRequest;
import bookmarks.controller.request.user.ChangePasswordRequest;
import bookmarks.controller.request.user.ChangeUserNameRequest;
import bookmarks.filter.FilterHelper;
import bookmarks.service.UserDeleteService;
import org.springframework.web.bind.annotation.*;

import bookmarks.controller.request.OneStringParamRequest;
import bookmarks.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private static final String CHANGE_PASSWORD_MAPPING = "user/password";
    private static final String CHANGE_USER_NAME_MAPPING = "user/name";
    private static final String DELETE_ACCOUNT_MAPPING = "user";
    private static final String USER_NAME_EXISTS_MAPPING = "user/name/exist";

    private final UserService userService;
    private final UserDeleteService userDeleteService;

    @PostMapping(CHANGE_PASSWORD_MAPPING)
    public void changePassword(
        @RequestBody @Valid ChangePasswordRequest request,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
        ){
        log.info("{} wants to chanhe his password", userId);
        userService.changePassword(request, userId);
    }

    @PostMapping(CHANGE_USER_NAME_MAPPING)
    public void changeUserName(
        @RequestBody @Valid ChangeUserNameRequest request,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to change his userName", userId);
        userService.changeUserName(request, userId);
    }

    @DeleteMapping(DELETE_ACCOUNT_MAPPING)
    public void deleteAccount(
        @RequestBody @Valid AccountDeleteRequest request,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to delete his account", userId);
        userDeleteService.deleteAccount(request, userId);
    }

    @PostMapping(USER_NAME_EXISTS_MAPPING)
    public boolean isUserNameExists(@RequestBody @Valid OneStringParamRequest request){
        log.info("Someone wants to know if userName {} is exists", request.getValue());
        return userService.isUserNameExists(request.getValue());
    }
}
