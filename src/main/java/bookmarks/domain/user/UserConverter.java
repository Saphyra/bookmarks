package bookmarks.domain.user;

import org.springframework.stereotype.Component;

import bookmarks.common.converter.ConverterBase;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserConverter extends ConverterBase<UserEntity, User> {

    @Override
    protected UserEntity convertDomainInternal(User domain) {
        return UserEntity.builder()
            .userId(domain.getUserId())
            .userName(domain.getUserName())
            .password(domain.getPassword())
            .build();
    }

    @Override
    protected User convertEntityInternal(UserEntity entity) {
        return User.builder()
            .userId(entity.getUserId())
            .userName(entity.getUserName())
            .password(entity.getPassword())
            .build();
    }
}
