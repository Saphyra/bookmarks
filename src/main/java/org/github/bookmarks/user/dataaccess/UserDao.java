package org.github.bookmarks.user.dataaccess;

import java.util.Optional;

import org.springframework.stereotype.Component;

import org.github.bookmarks.user.domain.BmUser;
import org.github.bookmarks.user.domain.BmUserConverter;
import org.github.bookmarks.user.domain.UserEntity;
import com.github.saphyra.dao.AbstractDao;

@Component
public class UserDao extends AbstractDao<UserEntity, BmUser, String, UserRepository> {
    public UserDao(BmUserConverter converter, UserRepository repository) {
        super(converter, repository);
    }

    public Optional<BmUser> findByUserName(String userName){
        return converter.convertEntity(repository.findByUserName(userName));
    }
}
