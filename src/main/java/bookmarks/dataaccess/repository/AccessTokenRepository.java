package bookmarks.dataaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookmarks.domain.accesstoken.AccessTokenEntity;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, String> {
}
