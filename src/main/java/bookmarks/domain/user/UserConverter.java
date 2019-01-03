package bookmarks.domain.user;

import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter extends ConverterBase<UserEntity, User> {

    @Override
    protected UserEntity processDomainConversion(User domain) {
        return UserEntity.builder()
            .userId(domain.getUserId())
            .userName(domain.getUserName())
            .password(domain.getPassword())
            .build();
    }

    @Override
    protected User processEntityConversion(UserEntity entity) {
        return User.builder()
            .userId(entity.getUserId())
            .userName(entity.getUserName())
            .password(entity.getPassword())
            .build();
    }
}
