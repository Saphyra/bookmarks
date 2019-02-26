package org.github.bookmarks.user.service;

import org.github.bookmarks.user.dataaccess.UserDao;
import org.springframework.stereotype.Service;

import org.github.bookmarks.user.domain.BmUser;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
class RegisterUserService {
    private final IdGenerator idGenerator;
    private final PasswordService passwordService;
    private final UserDao userDao;
    private final UserQueryService userQueryService;

    void register(String userName, String password) {
        log.info("Processing registration request for userName {}", userName);
        if (userQueryService.isUserNameExists(userName)) {
            throw new BadRequestException("BmUser name " + userName + " already exists.");
        }

        BmUser bmUser = BmUser.builder()
            .userId(idGenerator.generateRandomId())
            .userName(userName)
            .password(passwordService.hashPassword(password))
            .build();
        userDao.save(bmUser);
        log.info("BmUser saved successfully with id {}", bmUser.getUserId());
    }
}
