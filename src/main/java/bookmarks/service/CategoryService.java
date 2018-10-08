package bookmarks.service;

import java.util.List;

import org.springframework.stereotype.Service;

import bookmarks.common.exception.ForbiddenException;
import bookmarks.common.exception.NotFoundException;
import bookmarks.controller.request.CategoryRequest;
import bookmarks.dataaccess.CategoryDao;
import bookmarks.domain.category.Category;
import bookmarks.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryDao categoryDao;
    private final IdGenerator idGenerator;
    private final UserService userService;

    public void create(CategoryRequest request, String userId) {
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

    public void delete(String categoryId, String userId) {
        Category category = findByIdAuthorized(userId, categoryId);
        categoryDao.delete(category);
    }

    public List<Category> getCategoriesByRoot(String root) {
        return categoryDao.getByRoot(root);
    }

    public Category findByIdAuthorized(String userId, String categoryId) {
        Category category = categoryDao.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found with id " + categoryId));
        if(!category.getUserId().equals(userId)){
            throw  new ForbiddenException(userId + " has no access for category " + categoryId);
        }
        return category;
    }
}
