package bookmarks.domain.accesstoken;

import bookmarks.util.DateTimeUtil;
import com.github.saphyra.converter.ConverterBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BmAccessTokenConverter extends ConverterBase<AccessTokenEntity, BmAccessToken> {
    private final DateTimeUtil dateTimeUtil;

    @Override
    public BmAccessToken processEntityConversion(AccessTokenEntity entity) {
        return BmAccessToken.builder()
            .accessTokenId(entity.getAccessTokenId())
            .userId(entity.getUserId())
            .lastAccess(dateTimeUtil.convertEntity(entity.getLastAccess()))
            .persistent(entity.getPersistent())
            .build();
    }

    @Override
    public AccessTokenEntity processDomainConversion(BmAccessToken domain) {
        return AccessTokenEntity.builder()
            .accessTokenId(domain.getAccessTokenId())
            .userId(domain.getUserId())
            .lastAccess(dateTimeUtil.convertDomain(domain.getLastAccess()))
            .persistent(domain.getPersistent())
            .build();
    }
}
