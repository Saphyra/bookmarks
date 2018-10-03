package bookmarks.dataaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookmarks.domain.link.LinkEntity;
import bookmarks.domain.user.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
