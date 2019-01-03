package bookmarks.domain.accesstoken;

import bookmarks.util.DateTimeUtil;
import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessTokenConverter extends ConverterBase<AccessTokenEntity, AccessToken> {
    private final DateTimeUtil dateTimeUtil;

    @Override
    public AccessToken processEntityConversion(AccessTokenEntity entity) {
        return AccessToken.builder()
            .accessTokenId(entity.getAccessTokenId())
            .userId(entity.getUserId())
            .lastAccess(dateTimeUtil.convertEntity(entity.getLastAccess()))
            .persistent(entity.getPersistent())
            .build();
    }

    @Override
    public AccessTokenEntity processDomainConversion(AccessToken domain) {
        return AccessTokenEntity.builder()
            .accessTokenId(domain.getAccessTokenId())
            .userId(domain.getUserId())
            .lastAccess(dateTimeUtil.convertDomain(domain.getLastAccess()))
            .persistent(domain.getPersistent())
            .build();
    }
}
