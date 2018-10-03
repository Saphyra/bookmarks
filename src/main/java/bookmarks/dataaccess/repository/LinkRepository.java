package bookmarks.dataaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookmarks.domain.category.CategoryEntity;
import bookmarks.domain.link.LinkEntity;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, String> {
}
