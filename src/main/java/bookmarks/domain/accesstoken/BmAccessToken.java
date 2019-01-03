package bookmarks.domain.accesstoken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

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
