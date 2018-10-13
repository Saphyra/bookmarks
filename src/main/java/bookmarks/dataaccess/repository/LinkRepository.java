package bookmarks.dataaccess.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookmarks.domain.link.LinkEntity;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, String> {
    List<LinkEntity> getByRootAndUserId(String categoryId, String userId);

    List<LinkEntity> getByUserId(String userId);

    void deleteByUserId(String userId);
}
