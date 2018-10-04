package bookmarks.service;

import org.springframework.stereotype.Service;

import bookmarks.common.encryption.base.PasswordService;
import bookmarks.common.exception.BadRequestException;
import bookmarks.common.exception.NotFoundException;
import bookmarks.common.exception.UnauthorizedException;
import bookmarks.domain.accesstoken.AccessToken;
import bookmarks.domain.user.User;
import bookmarks.util.CookieUtil;
import bookmarks.util.DateTimeUtil;
import bookmarks.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AccessTokenService accessTokenService;
    private final CookieUtil cookieUtil;
    private final DateTimeUtil dateTimeUtil;
    private final IdGenerator idGenerator;
    private final PasswordService passwordService;
    private final UserService userService;

    public boolean isAuthenticated(String userId, String accessTokenId) {
        return true;
    }

    public AccessToken login(String userName, String password, Boolean remember) {
        User user = userService.findByUserName(userName)
            .orElseThrow(() -> new NotFoundException("User not found with name " + userName));
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
