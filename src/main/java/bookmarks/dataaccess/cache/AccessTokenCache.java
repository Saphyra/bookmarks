package bookmarks.dataaccess.cache;

import bookmarks.dataaccess.AccessTokenDao;
import bookmarks.domain.accesstoken.AccessToken;
import com.github.saphyra.cache.AbstractCache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class AccessTokenCache extends AbstractCache<String, AccessToken> {
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
