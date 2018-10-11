package bookmarks.dataaccess;

import java.util.List;

import org.springframework.stereotype.Component;

import bookmarks.common.AbstractDao;
import bookmarks.dataaccess.repository.CategoryRepository;
import bookmarks.domain.category.Category;
import bookmarks.domain.category.CategoryConverter;
import bookmarks.domain.category.CategoryEntity;

@Component
public class CategoryDao extends AbstractDao<CategoryEntity, Category, String, CategoryRepository> {
    public CategoryDao(CategoryConverter converter, CategoryRepository repository) {
        super(converter, repository);
    }

    public List<Category> getByRoot(String parentId){
        return converter.convertEntity(repository.getByRoot(parentId));
    }

    public List<Category> getByRootAndUserId(String parentId, String userId){
        return converter.convertEntity(repository.getByRootAndUserId(parentId, userId));
    }
}
