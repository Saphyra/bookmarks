package bookmarks.dataaccess;

import org.springframework.stereotype.Component;

import bookmarks.common.AbstractDao;
import bookmarks.dataaccess.repository.UserRepository;
import bookmarks.domain.user.User;
import bookmarks.domain.user.UserConverter;
import bookmarks.domain.user.UserEntity;

@Component
public class UserDao extends AbstractDao<UserEntity, User, String, UserRepository> {
    public UserDao(UserConverter converter, UserRepository repository) {
        super(converter, repository);
    }
}
