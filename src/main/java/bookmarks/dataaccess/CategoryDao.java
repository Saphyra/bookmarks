package bookmarks.dataaccess;

import bookmarks.dataaccess.repository.CategoryRepository;
import bookmarks.domain.category.Category;
import bookmarks.domain.category.CategoryConverter;
import bookmarks.domain.category.CategoryEntity;
import com.github.saphyra.dao.AbstractDao;
import org.springframework.stereotype.Component;

import java.util.List;

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
