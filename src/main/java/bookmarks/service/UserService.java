package bookmarks.service;

import bookmarks.controller.request.user.ChangePasswordRequest;
import bookmarks.controller.request.user.ChangeUserNameRequest;
import bookmarks.dataaccess.UserDao;
import bookmarks.domain.user.User;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserDao userDao;
    private final PasswordService passwordService;

    public void changePassword(ChangePasswordRequest request, String userId) {
        User user = findByUserIdAuthorized(userId);
        if(!passwordService.authenticate(request.getOldPassword(), user.getPassword())){
            throw new UnauthorizedException("Bad old password");
        }
        user.setPassword(passwordService.hashPassword(request.getNewPassword()));
        userDao.save(user);
    }

    public void changeUserName(ChangeUserNameRequest request, String userId) {
        if(isUserNameExists(request.getNewUserName())){
            throw new BadRequestException(request.getNewUserName() + " userName is already exist.");
        }
        User user = findByUserIdAuthorized(userId);
        if(!passwordService.authenticate(request.getPassword(), user.getPassword())){
            throw new UnauthorizedException("Bad old password");
        }
        user.setUserName(request.getNewUserName());
        userDao.save(user);
    }

    public Optional<User> findByUserId(String userId) {
        return userDao.findById(userId);
    }

    public User findByUserIdAuthorized(String userId) {
        return findByUserId(userId).orElseThrow(() -> new NotFoundException("User not found with userId " + userId));
    }

    public Optional<User> findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    public boolean isUserNameExists(String userName) {
        return userDao.findByUserName(userName).isPresent();
    }

    public void save(User user) {
        userDao.save(user);
    }
}
