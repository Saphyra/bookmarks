package bookmarks.dataaccess;

import bookmarks.dataaccess.repository.AccessTokenRepository;
import bookmarks.domain.accesstoken.BmAccessToken;
import bookmarks.domain.accesstoken.BmAccessTokenConverter;
import bookmarks.domain.accesstoken.AccessTokenEntity;
import bookmarks.util.DateTimeUtil;
import com.github.saphyra.dao.AbstractDao;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class AccessTokenDao extends AbstractDao<AccessTokenEntity, BmAccessToken, String, AccessTokenRepository> {
    private final DateTimeUtil dateTimeUtil;

    public AccessTokenDao(BmAccessTokenConverter converter, AccessTokenRepository repository, DateTimeUtil dateTimeUtil) {
        super(converter, repository);
        this.dateTimeUtil = dateTimeUtil;
    }

    public void deleteByUserId(String userId){
        repository.deleteByUserId(userId);
    }

    public void deleteExpired(OffsetDateTime expiration) {
        repository.deleteExpired(dateTimeUtil.convertDomain(expiration));
    }
}
