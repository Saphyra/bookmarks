package bookmarks.service;

import bookmarks.common.encryption.PasswordService;
import bookmarks.common.exception.UnauthorizedException;
import bookmarks.controller.request.user.AccountDeleteRequest;
import bookmarks.dataaccess.AccessTokenDao;
import bookmarks.dataaccess.CategoryDao;
import bookmarks.dataaccess.LinkDao;
import bookmarks.dataaccess.UserDao;
import bookmarks.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserDeleteService {
    private final AccessTokenDao accessTokenDao;
    private final CategoryDao categoryDao;
    private final LinkDao linkDao;
    private final PasswordService passwordService;
    private final UserDao userDao;
    private final UserService userService;


    @Transactional
    public void deleteAccount(AccountDeleteRequest request, String userId) {
        User user = userService.findByUserIdAuthorized(userId);
        if(!passwordService.authenticate(request.getPassword(), user.getPassword())){
            throw new UnauthorizedException("bad password");
        }
        accessTokenDao.deleteByUserId(userId);
        categoryDao.deleteByUserId(userId);
        linkDao.deleteByUserId(userId);
        userDao.delete(user);
    }
}
