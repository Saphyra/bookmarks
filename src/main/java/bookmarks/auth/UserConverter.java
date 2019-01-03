package bookmarks.auth;

import bookmarks.domain.user.BmUser;
import com.github.saphyra.authservice.domain.Credentials;
import com.github.saphyra.authservice.domain.User;
import com.github.saphyra.converter.ConverterBase;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserConverter extends ConverterBase<BmUser, User> {
    @Override
    protected User processEntityConversion(BmUser bmUser) {
        return User.builder()
            .userId(bmUser.getUserId())
            .credentials(
                Credentials.builder()
                    .userName(bmUser.getUserName())
                    .password(bmUser.getPassword())
                    .build()
            )
            .roles(new HashSet<>())
            .build();
    }

    @Override
    protected BmUser processDomainConversion(User user) {
        throw new UnsupportedOperationException("User cannot be converted to BmUser.");
    }
}
