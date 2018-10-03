package bookmarks.domain.accesstoken;

import org.springframework.stereotype.Component;

import bookmarks.common.converter.ConverterBase;
import bookmarks.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccessTokenConverter extends ConverterBase<AccessTokenEntity, AccessToken> {
    private final DateTimeUtil dateTimeUtil;

    @Override
    public AccessToken convertEntityInternal(AccessTokenEntity entity) {
        return AccessToken.builder()
            .accessTokenId(entity.getAccessTokenId())
            .userId(entity.getUserId())
            .lastAccess(dateTimeUtil.convertEntity(entity.getLastAccess()))
            .build();
    }

    @Override
    public AccessTokenEntity convertDomainInternal(AccessToken domain) {
        return AccessTokenEntity.builder()
            .accessTokenId(domain.getAccessTokenId())
            .userId(domain.getUserId())
            .lastAccess(dateTimeUtil.convertDomain(domain.getLastAccess()))
            .build();
    }
}
