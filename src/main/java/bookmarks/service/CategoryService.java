package bookmarks.service;

import bookmarks.controller.request.CreateCategoryRequest;
import bookmarks.dataaccess.CategoryDao;
import bookmarks.domain.category.Category;
import bookmarks.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryDao categoryDao;
    private final IdGenerator idGenerator;
    private final UserService userService;

    public void create(CreateCategoryRequest request, String userId) {
        userService.findByUserIdAuthorized(userId);

        Category category = Category.builder()
            .categoryId(idGenerator.getRandomId())
            .root(request.getRoot())
            .userId(userId)
            .label(request.getLabel())
            .description(request.getDescription())
            .build();
        categoryDao.save(category);
    }

    public List<Category> getCategoriesByRoot(String root) {
        return categoryDao.getByRoot(root);
    }
}
