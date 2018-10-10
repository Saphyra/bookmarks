package bookmarks.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import bookmarks.common.exception.ForbiddenException;
import bookmarks.common.exception.NotFoundException;
import bookmarks.controller.request.CategoryRequest;
import bookmarks.dataaccess.CategoryDao;
import bookmarks.domain.category.Category;
import bookmarks.util.CategoryUtil;
import bookmarks.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryUtil categoryUtil;
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

    @Transactional
    public void delete(List<String> categoryIds, String userId) {
        categoryIds.forEach(categoryId ->{
            Category category = findByIdAuthorized(userId, categoryId);
            categoryDao.delete(category);
        });
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

    public void update(CategoryRequest request, String categoryId, String userId) {
        Category category = findByIdAuthorized(userId, categoryId);

        categoryUtil.validateRoot(request.getRoot(), userId);

        category.setLabel(request.getLabel());
        category.setDescription(request.getDescription());
        category.setRoot(request.getRoot());

        categoryDao.save(category);
    }
}
