package org.github.bookmarks.auth.domain;

import org.springframework.stereotype.Component;

import com.github.saphyra.authservice.domain.AccessToken;
import com.github.saphyra.converter.ConverterBase;

@Component
public class AccessTokenConverter extends ConverterBase<BmAccessToken, AccessToken> {
    @Override
    protected AccessToken processEntityConversion(BmAccessToken bmAccessToken) {
        return AccessToken.builder()
            .accessTokenId(bmAccessToken.getAccessTokenId())
            .userId(bmAccessToken.getUserId())
            .isPersistent(bmAccessToken.getPersistent())
            .lastAccess(bmAccessToken.getLastAccess())
            .build();
    }

    @Override
    protected BmAccessToken processDomainConversion(AccessToken accessToken) {
        return BmAccessToken.builder()
            .accessTokenId(accessToken.getAccessTokenId())
            .userId(accessToken.getUserId())
            .persistent(accessToken.isPersistent())
            .lastAccess(accessToken.getLastAccess())
            .build();
    }
}
