package bookmarks.service;

import bookmarks.domain.user.BmUser;
import com.github.saphyra.encryption.impl.PasswordService;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final IdGenerator idGenerator;
    private final PasswordService passwordService;
    private final UserService userService;

    public void register(String userName, String password) {
        log.info("Processing registration request for userName {}", userName);
        if (userService.isUserNameExists(userName)) {
            throw new BadRequestException("BmUser name " + userName + " already exists.");
        }

        BmUser bmUser = BmUser.builder()
            .userId(idGenerator.generateRandomId())
            .userName(userName)
            .password(passwordService.hashPassword(password))
            .build();
        userService.save(bmUser);
        log.info("BmUser saved successfully with id {}", bmUser.getUserId());
    }
}
