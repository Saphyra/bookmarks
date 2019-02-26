package org.github.bookmarks.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import org.github.bookmarks.user.dataaccess.UserDao;
import org.github.bookmarks.user.domain.BmUser;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class UserQueryService {
    private final UserDao userDao;

    BmUser findByUserIdAuthorized(String userId) {
        return findByUserId(userId).orElseThrow(() -> new NotFoundException("BmUser not found with userId " + userId));
    }

    Optional<BmUser> findByUserId(String userId) {
        return userDao.findById(userId);
    }

    boolean isUserNameExists(String userName) {
        return userDao.findByUserName(userName).isPresent();
    }

    Optional<BmUser> findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }
}
