package bookmarks.authentication;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationFacade {
    public void login(String userName, String password){

    }

    public void logout(String userId, String accessTokenId) {
    }

    public boolean isAuthenticated(String userId, String accessTokenId) {
        return true;
    }
}
