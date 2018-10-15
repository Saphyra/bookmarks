package bookmarks.service;

import org.springframework.stereotype.Service;

import bookmarks.common.encryption.base.PasswordService;
import bookmarks.common.exception.BadRequestException;
import bookmarks.common.exception.NotFoundException;
import bookmarks.common.exception.UnauthorizedException;
import bookmarks.dataaccess.cache.AccessTokenCache;
import bookmarks.domain.accesstoken.AccessToken;
import bookmarks.domain.user.User;
import bookmarks.util.DateTimeUtil;
import bookmarks.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AccessTokenCache accessTokenCache;
    private final AccessTokenService accessTokenService;
    private final DateTimeUtil dateTimeUtil;
    private final IdGenerator idGenerator;
    private final PasswordService passwordService;
    private final UserService userService;

    public boolean isAuthenticated(String userId, String accessTokenId) {
        try {
            AccessToken accessToken = accessTokenCache.get(accessTokenId)
                .orElseThrow(() -> new UnauthorizedException("AccessToken not found with accessTokenId " + accessTokenId));
            validateUserId(accessToken, userId);
            validateExpiration(accessToken);

            userService.findByUserIdAuthorized(userId);

            accessToken.setLastAccess(dateTimeUtil.now());
            accessTokenService.save(accessToken);
        } catch (RuntimeException e) {
            log.warn("Authorization failed:", e);
            return false;
        }
        log.debug("Authorization successful");
        return true;
    }

    private void validateExpiration(AccessToken accessToken) {
        if(!accessToken.getPersistent() && accessToken.getLastAccess().isBefore(dateTimeUtil.getExpirationDate())){
            throw new UnauthorizedException("AccessToken is expired.");
        }
    }

    public AccessToken login(String userName, String password, Boolean remember) {
        User user = userService.findByUserName(userName)
            .orElseThrow(() -> new UnauthorizedException("User not found with name " + userName));
        if (!passwordService.authenticate(password, user.getPassword())) {
            throw new UnauthorizedException("Bad password entered for user " + userName);
        }

        AccessToken accessToken = AccessToken.builder()
            .accessTokenId(idGenerator.getRandomId())
            .userId(user.getUserId())
            .lastAccess(dateTimeUtil.now())
            .persistent(remember)
            .build();

        accessTokenService.save(accessToken);
        return accessToken;
    }

    public void logout(String userId, String accessTokenId) {
        if (accessTokenId == null) {
            throw new BadRequestException("accessTokenId must not be null.");
        }
        if (userId == null) {
            throw new BadRequestException("userId must not be null.");
        }

        AccessToken accessToken = accessTokenCache.get(accessTokenId).orElse(null);
        if(accessToken == null){
            log.info("AccessToken not found with accessTokenId " + accessTokenId);
            return;
        }
        validateUserId(accessToken, userId);

        accessTokenCache.invalidate(accessToken.getAccessTokenId());
        accessTokenService.delete(accessToken);
    }

    private void validateUserId(AccessToken accessToken, String userId) {
        if (!userId.equals(accessToken.getUserId())) {
            throw new UnauthorizedException("UserId " + userId + " is not equals the user id contained by accessToken with accessTokenId " + accessToken.getAccessTokenId());
        }
    }

    public void register(String userName, String password) {
        log.info("Processing registration request for userName {}", userName);
        if (userService.isUserNameExists(userName)) {
            throw new BadRequestException("User name " + userName + " already exists.");
        }

        User user = User.builder()
            .userId(idGenerator.getRandomId())
            .userName(userName)
            .password(passwordService.hashPassword(password))
            .build();
        userService.save(user);
        log.info("User saved successfully with id {}", user.getUserId());
    }
}
