package bookmarks.authentication;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationFacade {
    public void logout(String userIdValue, String accessTokenId) {
    }

    public boolean isAuthenticated(String userIdValue, String accessTokenId) {
        return true;
    }
}
