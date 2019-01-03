package bookmarks.service;

import bookmarks.controller.request.user.AccountDeleteRequest;
import bookmarks.dataaccess.AccessTokenDao;
import bookmarks.dataaccess.CategoryDao;
import bookmarks.dataaccess.LinkDao;
import bookmarks.dataaccess.UserDao;
import bookmarks.domain.user.BmUser;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
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
        BmUser bmUser = userService.findByUserIdAuthorized(userId);
        if(!passwordService.authenticate(request.getPassword(), bmUser.getPassword())){
            throw new UnauthorizedException("bad password");
        }
        accessTokenDao.deleteByUserId(userId);
        categoryDao.deleteByUserId(userId);
        linkDao.deleteByUserId(userId);
        userDao.delete(bmUser);
    }
}
