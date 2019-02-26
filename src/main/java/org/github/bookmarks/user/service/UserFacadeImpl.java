package org.github.bookmarks.user.service;

import java.util.Optional;

import org.github.bookmarks.user.UserFacade;
import org.github.bookmarks.user.controller.request.AccountDeleteRequest;
import org.github.bookmarks.user.controller.request.ChangePasswordRequest;
import org.github.bookmarks.user.controller.request.ChangeUserNameRequest;
import org.springframework.stereotype.Service;

import org.github.bookmarks.user.domain.BmUser;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
    private final ChangePasswordService changePasswordService;
    private final ChangeUserNameService changeUserNameService;
    private final DeleteUserService deleteUserService;
    private final RegisterUserService registerUserService;
    private final UserQueryService userQueryService;

    public void changePassword(ChangePasswordRequest request, String userId) {
        changePasswordService.changePassword(request, userId);
    }

    public void changeUserName(ChangeUserNameRequest request, String userId) {
        changeUserNameService.changeUserName(request, userId);
    }

    @Override
    public void checkUserWithIdExists(String userId) {
        userQueryService.findByUserIdAuthorized(userId);
    }

    @Override
    public void deleteAccount(AccountDeleteRequest request, String userId) {
        deleteUserService.deleteAccount(request, userId);
    }

    @Override
    public Optional<BmUser> findById(String userId) {
        return userQueryService.findByUserId(userId);
    }

    @Override
    public Optional<BmUser> findByUserName(String userName) {
        return userQueryService.findByUserName(userName);
    }

    @Override
    public boolean isUserNameExists(String userName) {
        return userQueryService.isUserNameExists(userName);
    }

    @Override
    public void register(String userName, String password) {
        registerUserService.register(userName, password);
    }
}
