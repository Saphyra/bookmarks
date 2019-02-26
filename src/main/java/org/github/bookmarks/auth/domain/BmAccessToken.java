package org.github.bookmarks.auth.domain;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BmAccessToken {
    private String accessTokenId;
    private String userId;
    private OffsetDateTime lastAccess;
    private Boolean persistent;
}
