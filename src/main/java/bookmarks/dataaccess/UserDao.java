package bookmarks.dataaccess;

import bookmarks.dataaccess.repository.UserRepository;
import bookmarks.domain.user.User;
import bookmarks.domain.user.UserConverter;
import bookmarks.domain.user.UserEntity;
import com.github.saphyra.dao.AbstractDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDao extends AbstractDao<UserEntity, User, String, UserRepository> {
    public UserDao(UserConverter converter, UserRepository repository) {
        super(converter, repository);
    }

    public Optional<User> findByUserName(String userName){
        return converter.convertEntity(repository.findByUserName(userName));
    }
}
