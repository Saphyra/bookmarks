package bookmarks.dataaccess;

import bookmarks.dataaccess.repository.UserRepository;
import bookmarks.domain.user.BmUser;
import bookmarks.domain.user.BmUserConverter;
import bookmarks.domain.user.UserEntity;
import com.github.saphyra.dao.AbstractDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDao extends AbstractDao<UserEntity, BmUser, String, UserRepository> {
    public UserDao(BmUserConverter converter, UserRepository repository) {
        super(converter, repository);
    }

    public Optional<BmUser> findByUserName(String userName){
        return converter.convertEntity(repository.findByUserName(userName));
    }
}
