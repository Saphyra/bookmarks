package bookmarks.domain.accesstoken;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    private String accessTokenId;
    private String userId;
    private LocalDateTime lastAccess;
    private Boolean persistent;
}
