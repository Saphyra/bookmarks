package org.github.bookmarks.user.service;

import org.github.bookmarks.user.controller.request.ChangeUserNameRequest;
import org.github.bookmarks.user.dataaccess.UserDao;
import org.springframework.stereotype.Service;

import org.github.bookmarks.user.domain.BmUser;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
class ChangeUserNameService {
    private final PasswordService passwordService;
    private final UserDao userDao;
    private final UserQueryService userQueryService;

    void changeUserName(ChangeUserNameRequest request, String userId) {
        if (userQueryService.isUserNameExists(request.getNewUserName())) {
            throw new BadRequestException(request.getNewUserName() + " userName is already exist.");
        }
        BmUser bmUser = userQueryService.findByUserIdAuthorized(userId);
        if (!passwordService.authenticate(request.getPassword(), bmUser.getPassword())) {
            throw new UnauthorizedException("Bad old password");
        }
        bmUser.setUserName(request.getNewUserName());
        userDao.save(bmUser);
    }
}
