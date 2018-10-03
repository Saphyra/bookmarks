package bookmarks.dataaccess;

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
}
