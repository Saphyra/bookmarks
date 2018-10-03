package bookmarks.dataaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookmarks.domain.accesstoken.AccessTokenEntity;
import bookmarks.domain.category.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
}
