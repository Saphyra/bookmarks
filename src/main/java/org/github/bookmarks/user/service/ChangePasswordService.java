package org.github.bookmarks.user.service;

import org.github.bookmarks.user.controller.request.ChangePasswordRequest;
import org.springframework.stereotype.Service;

import org.github.bookmarks.user.dataaccess.UserDao;

import org.github.bookmarks.user.domain.BmUser;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class ChangePasswordService {
    private final PasswordService passwordService;
    private final UserDao userDao;
    private final UserQueryService userQueryService;

    void changePassword(ChangePasswordRequest request, String userId) {
        BmUser bmUser = userQueryService.findByUserIdAuthorized(userId);
        if (!passwordService.authenticate(request.getOldPassword(), bmUser.getPassword())) {
            throw new UnauthorizedException("Bad old password");
        }
        bmUser.setPassword(passwordService.hashPassword(request.getNewPassword()));
        userDao.save(bmUser);
    }
}
