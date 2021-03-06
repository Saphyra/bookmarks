package org.github.bookmarks.auth;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.github.bookmarks.auth.domain.AccessTokenConverter;
import org.github.bookmarks.user.UserFacade;
import org.springframework.stereotype.Service;

import org.github.bookmarks.auth.dataaccess.AccessTokenDao;
import com.github.saphyra.authservice.AuthDao;
import com.github.saphyra.authservice.domain.AccessToken;
import com.github.saphyra.authservice.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthDaoImpl implements AuthDao {
    private final AccessTokenConverter accessTokenConverter;
    private final AccessTokenDao accessTokenDao;
    private final UserConverter userConverter;
    private final UserFacade userFacade;

    @Override
    public Optional<User> findUserById(String userId) {
        return userConverter.convertEntity(userFacade.findById(userId));
    }

    @Override
    public Optional<User> findUserByUserName(String userName) {
        return userConverter.convertEntity(userFacade.findByUserName(userName));
    }

    @Override
    public void deleteAccessToken(AccessToken accessToken) {
        accessTokenDao.deleteById(accessToken.getAccessTokenId());
    }

    @Override
    public void deleteAccessTokenByUserId(String userId) {
        throw new UnsupportedOperationException("Deletion of all AccessToken's of domain is not allowed.");
    }

    @Override
    public void deleteExpiredAccessTokens(OffsetDateTime expiration) {
        accessTokenDao.deleteExpired(expiration);
    }

    @Override
    public Optional<AccessToken> findAccessTokenByTokenId(String accessTokenId) {
        return accessTokenConverter.convertEntity(accessTokenDao.findById(accessTokenId));
    }

    @Override
    public void saveAccessToken(AccessToken accessToken) {
        accessTokenDao.save(accessTokenConverter.convertDomain(accessToken));
    }
}
