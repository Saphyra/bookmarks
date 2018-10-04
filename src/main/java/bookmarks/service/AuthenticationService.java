package bookmarks.service;

import org.springframework.stereotype.Service;

import bookmarks.common.encryption.base.PasswordService;
import bookmarks.common.exception.BadRequestException;
import bookmarks.domain.user.User;
import bookmarks.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final IdGenerator idGenerator;
    private final PasswordService passwordService;
    private final UserService userService;

    public boolean isAuthenticated(String userId, String accessTokenId) {
        return true;
    }

    public void login(String userName, String password, Boolean remember){

    }

    public void logout(String userId, String accessTokenId) {
    }

    public void register(String userName, String password){
        log.info("Processing registration request for userName {}", userName);
        if(userService.isUserNameExists(userName)){
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
