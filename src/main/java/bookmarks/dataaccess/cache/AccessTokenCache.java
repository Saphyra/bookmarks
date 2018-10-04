package bookmarks.dataaccess.cache;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import bookmarks.common.AbstractCache;
import bookmarks.dataaccess.AccessTokenDao;
import bookmarks.domain.accesstoken.AccessToken;
import com.google.common.cache.CacheBuilder;

@Component
public class AccessTokenCache extends AbstractCache<String, Optional<AccessToken>> {
    private final AccessTokenDao accessTokenDao;

    public AccessTokenCache(AccessTokenDao accessTokenDao) {
        super(
            CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .build()
        );
        this.accessTokenDao = accessTokenDao;
    }

    @Override
    public Optional<AccessToken> get(String accessTokenId) {
        return get(accessTokenId, () -> accessTokenDao.findById(accessTokenId));
    }
}
