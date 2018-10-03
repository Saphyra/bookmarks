package bookmarks.domain.accesstoken;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "access_token")
public class AccessTokenEntity {
    @Id
    @Column(name = "access_token_id", length = 50)
    private String accessTokenId;

    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "last_access", nullable = false)
    private Long lastAccess;
}
