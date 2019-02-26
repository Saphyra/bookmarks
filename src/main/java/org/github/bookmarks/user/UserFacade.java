package org.github.bookmarks.user;

import java.util.Optional;

import org.github.bookmarks.user.controller.request.AccountDeleteRequest;
import org.github.bookmarks.user.controller.request.ChangePasswordRequest;
import org.github.bookmarks.user.controller.request.ChangeUserNameRequest;

import org.github.bookmarks.user.domain.BmUser;

public interface UserFacade {
    void changePassword(ChangePasswordRequest request, String userId);

    void changeUserName(ChangeUserNameRequest request, String userId);

    void checkUserWithIdExists(String userId);

    void deleteAccount(AccountDeleteRequest request, String userId);

    Optional<BmUser> findById(String userId);

    Optional<BmUser> findByUserName(String userName);

    boolean isUserNameExists(String value);

    void register(String userName, String password);
}
