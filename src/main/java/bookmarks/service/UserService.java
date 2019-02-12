package bookmarks.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import bookmarks.controller.request.user.ChangePasswordRequest;
import bookmarks.controller.request.user.ChangeUserNameRequest;
import bookmarks.dataaccess.UserDao;
import bookmarks.domain.user.BmUser;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserDao userDao;
    private final PasswordService passwordService;
    private final IdGenerator idGenerator;

    public void changePassword(ChangePasswordRequest request, String userId) {
        BmUser bmUser = findByUserIdAuthorized(userId);
        if(!passwordService.authenticate(request.getOldPassword(), bmUser.getPassword())){
            throw new UnauthorizedException("Bad old password");
        }
        bmUser.setPassword(passwordService.hashPassword(request.getNewPassword()));
        userDao.save(bmUser);
    }

    public void changeUserName(ChangeUserNameRequest request, String userId) {
        if(isUserNameExists(request.getNewUserName())){
            throw new BadRequestException(request.getNewUserName() + " userName is already exist.");
        }
        BmUser bmUser = findByUserIdAuthorized(userId);
        if(!passwordService.authenticate(request.getPassword(), bmUser.getPassword())){
            throw new UnauthorizedException("Bad old password");
        }
        bmUser.setUserName(request.getNewUserName());
        userDao.save(bmUser);
    }

    public Optional<BmUser> findByUserId(String userId) {
        return userDao.findById(userId);
    }

    public BmUser findByUserIdAuthorized(String userId) {
        return findByUserId(userId).orElseThrow(() -> new NotFoundException("BmUser not found with userId " + userId));
    }

    public Optional<BmUser> findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    public boolean isUserNameExists(String userName) {
        return userDao.findByUserName(userName).isPresent();
    }

    public void register(String userName, String password) {
        log.info("Processing registration request for userName {}", userName);
        if (isUserNameExists(userName)) {
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

    public Optional<BmUser> findById(String userId) {
        return userDao.findById(userId);
    }
}
