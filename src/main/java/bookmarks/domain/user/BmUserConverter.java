package bookmarks.domain.user;

import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BmUserConverter extends ConverterBase<UserEntity, BmUser> {

    @Override
    protected UserEntity processDomainConversion(BmUser domain) {
        return UserEntity.builder()
            .userId(domain.getUserId())
            .userName(domain.getUserName())
            .password(domain.getPassword())
            .build();
    }

    @Override
    protected BmUser processEntityConversion(UserEntity entity) {
        return BmUser.builder()
            .userId(entity.getUserId())
            .userName(entity.getUserName())
            .password(entity.getPassword())
            .build();
    }
}
