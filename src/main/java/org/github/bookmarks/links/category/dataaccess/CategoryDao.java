package org.github.bookmarks.links.category.dataaccess;

import java.util.List;

import org.github.bookmarks.links.category.domain.Category;
import org.github.bookmarks.links.category.domain.CategoryConverter;
import org.github.bookmarks.links.category.domain.CategoryEntity;
import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;

@Component
public class CategoryDao extends AbstractDao<CategoryEntity, Category, String, CategoryRepository> {
    public CategoryDao(CategoryConverter converter, CategoryRepository repository) {
        super(converter, repository);
    }

    public void deleteByUserId(String userId){
        repository.deleteByUserId(userId);
    }

    public List<Category> getByUserId(String userId){
        return converter.convertEntity(repository.getByUserId(userId));
    }

    public List<Category> getByRoot(String parentId){
        return converter.convertEntity(repository.getByRoot(parentId));
    }

    public List<Category> getByRootAndUserId(String parentId, String userId){
        return converter.convertEntity(repository.getByRootAndUserId(parentId, userId));
    }
}
