package bookmarks.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bookmarks.controller.request.OneStringParamRequest;
import bookmarks.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private static final String USER_NAME_EXISTS_MAPPING = "user/name/exist";

    private final UserService userService;

    @PostMapping(USER_NAME_EXISTS_MAPPING)
    public boolean isUserNameExists(@RequestBody @Valid OneStringParamRequest request){
        log.info("Someone wants to know if userName {} is exists", request.getValue());
        return userService.isUserNameExists(request.getValue());
    }
}
