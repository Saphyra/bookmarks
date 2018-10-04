package bookmarks.service;

import org.springframework.stereotype.Service;

import bookmarks.dataaccess.AccessTokenDao;
import bookmarks.domain.accesstoken.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class AccessTokenService {
    private final AccessTokenDao accessTokenDao;

    public void save(AccessToken accessToken) {
        accessTokenDao.save(accessToken);
    }
}
