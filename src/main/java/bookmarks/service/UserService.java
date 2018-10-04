package bookmarks.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import bookmarks.common.exception.NotFoundException;
import bookmarks.dataaccess.UserDao;
import bookmarks.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserDao userDao;

    public Optional<User> findByUserId(String userId) {
        return userDao.findById(userId);
    }

    public User findByUserIdAuthorized(String userId){
        return findByUserId(userId).orElseThrow(()-> new NotFoundException("User not found with userId " + userId));
    }

    public Optional<User> findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    public boolean isUserNameExists(String userName){
        return userDao.findByUserName(userName).isPresent();
    }

    public void save(User user) {
        userDao.save(user);
    }
}
