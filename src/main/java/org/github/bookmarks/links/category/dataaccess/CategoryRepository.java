package org.github.bookmarks.links.category.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.github.bookmarks.links.category.domain.CategoryEntity;

@Repository
interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    List<CategoryEntity> getByRootAndUserId(String parentId, String userId);

    List<CategoryEntity> getByRoot(String parentId);

    List<CategoryEntity> getByUserId(String userId);

    void deleteByUserId(String userId);
}
