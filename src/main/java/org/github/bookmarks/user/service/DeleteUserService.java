package org.github.bookmarks.user.service;

import javax.transaction.Transactional;

import org.github.bookmarks.user.controller.request.AccountDeleteRequest;
import org.github.bookmarks.user.dataaccess.UserDao;
import org.springframework.stereotype.Service;

import org.github.bookmarks.auth.dataaccess.AccessTokenDao;
import bookmarks.dataaccess.CategoryDao;
import bookmarks.dataaccess.LinkDao;
import org.github.bookmarks.user.domain.BmUser;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
class DeleteUserService {
    private final AccessTokenDao accessTokenDao;
    private final CategoryDao categoryDao;
    private final LinkDao linkDao;
    private final PasswordService passwordService;
    private final UserDao userDao;
    private final UserQueryService userQueryService;

    @Transactional
    void deleteAccount(AccountDeleteRequest request, String userId) {
        BmUser bmUser = userQueryService.findByUserIdAuthorized(userId);
        if(!passwordService.authenticate(request.getPassword(), bmUser.getPassword())){
            throw new UnauthorizedException("bad password");
        }
        accessTokenDao.deleteByUserId(userId);
        categoryDao.deleteByUserId(userId);
        linkDao.deleteByUserId(userId);
        userDao.delete(bmUser);
    }
}
