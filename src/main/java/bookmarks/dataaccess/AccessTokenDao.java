package bookmarks.dataaccess;

import bookmarks.dataaccess.repository.AccessTokenRepository;
import bookmarks.domain.accesstoken.AccessToken;
import bookmarks.domain.accesstoken.AccessTokenConverter;
import bookmarks.domain.accesstoken.AccessTokenEntity;
import com.github.saphyra.dao.AbstractDao;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenDao extends AbstractDao<AccessTokenEntity, AccessToken, String, AccessTokenRepository> {
    public AccessTokenDao(AccessTokenConverter converter, AccessTokenRepository repository) {
        super(converter, repository);
    }

    public void deleteByUserId(String userId){
        repository.deleteByUserId(userId);
    }

    public void deleteExpired(Long expiration) {
        repository.deleteExpired(expiration);
    }
}
