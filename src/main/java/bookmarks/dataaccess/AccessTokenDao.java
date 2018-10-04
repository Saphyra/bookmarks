package bookmarks.dataaccess;

import org.springframework.stereotype.Component;

import bookmarks.common.AbstractDao;
import bookmarks.dataaccess.repository.AccessTokenRepository;
import bookmarks.domain.accesstoken.AccessToken;
import bookmarks.domain.accesstoken.AccessTokenConverter;
import bookmarks.domain.accesstoken.AccessTokenEntity;

@Component
public class AccessTokenDao extends AbstractDao<AccessTokenEntity, AccessToken, String, AccessTokenRepository> {
    public AccessTokenDao(AccessTokenConverter converter, AccessTokenRepository repository) {
        super(converter, repository);
    }

    public void deleteExpired(Long expiration) {
        repository.deleteExpired(expiration);
    }
}
