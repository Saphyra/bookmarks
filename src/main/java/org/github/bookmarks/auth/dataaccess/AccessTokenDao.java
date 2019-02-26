package org.github.bookmarks.auth.dataaccess;

import java.time.OffsetDateTime;

import org.github.bookmarks.auth.domain.AccessTokenEntity;
import org.github.bookmarks.auth.domain.BmAccessToken;
import org.github.bookmarks.auth.domain.BmAccessTokenConverter;
import org.springframework.stereotype.Component;

import org.github.bookmarks.common.util.DateTimeUtil;
import com.github.saphyra.dao.AbstractDao;

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
