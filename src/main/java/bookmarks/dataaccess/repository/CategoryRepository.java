package bookmarks.dataaccess.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookmarks.domain.category.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    List<CategoryEntity> getByParentId(String parentId);
}
