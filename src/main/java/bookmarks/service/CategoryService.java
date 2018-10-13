package bookmarks.service;

import bookmarks.common.exception.BadRequestException;
import bookmarks.common.exception.ForbiddenException;
import bookmarks.common.exception.NotFoundException;
import bookmarks.controller.request.CreateCategoryRequest;
import bookmarks.controller.request.UpdateCategoryRequest;
import bookmarks.controller.response.DataResponse;
import bookmarks.controller.response.DataTreeResponse;
import bookmarks.dataaccess.CategoryDao;
import bookmarks.dataaccess.LinkDao;
import bookmarks.domain.category.Category;
import bookmarks.domain.link.Link;
import bookmarks.util.CategoryUtil;
import bookmarks.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static bookmarks.util.Util.replaceIfNotNull;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryUtil categoryUtil;
    private final LinkDao linkDao;
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

    @Transactional
    public void delete(List<String> categoryIds, String userId) {
        categoryIds.forEach(categoryId -> {
            Category category = findByIdAuthorized(userId, categoryId);
            updateChildren(categoryId, userId);
            categoryDao.delete(category);
        });
    }

    private void updateChildren(String categoryId, String userId) {
        List<Category> categories = categoryDao.getByRootAndUserId(categoryId, userId);

        categories.forEach(category -> {
            category.setRoot("");
            categoryDao.save(category);
        });

        List<Link> links = linkDao.getByRootAndUserId(categoryId, userId);
        links.forEach(link -> {
            link.setRoot("");
            linkDao.save(link);
        });
    }

    public Category findByIdAuthorized(String userId, String categoryId) {
        Category category = categoryDao.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found with id " + categoryId));
        if (!category.getUserId().equals(userId)) {
            throw new ForbiddenException(userId + " has no access for category " + categoryId);
        }
        return category;
    }

    public List<Category> getCategoriesByRootAndUserId(String root, String userId) {
        return categoryDao.getByRootAndUserId(root, userId);
    }

    public DataResponse getCategory(String userId, String categoryId) {
        Category category = findByIdAuthorized(userId, categoryId);
        return DataResponse.builder()
            .type(DataResponse.Type.CATEGORY)
            .element(category)
            .build();
    }

    public List<DataTreeResponse> getCategoryTree(String userId) {
        DataTreeResponse root = new DataTreeResponse();
        root.setCategory(
            Category.builder()
                .categoryId("")
                .label("Root")
                .build()
        );
        root.setChildren(getCategoryTree("", userId));
        return Arrays.asList(root);
    }

    private List<DataTreeResponse> getCategoryTree(String root, String userId) {
        return categoryDao.getByRootAndUserId(root, userId).stream()
            .map(category -> {
                DataTreeResponse response = new DataTreeResponse();
                response.setCategory(category);
                response.setChildren(getCategoryTree(category.getCategoryId(), userId));
                return response;
            })
            .collect(Collectors.toList());
    }

    @Transactional
    public void update(List<UpdateCategoryRequest> requests, String userId) {
        requests.forEach(request -> {
            Category category = findByIdAuthorized(userId, request.getCategoryId());

            categoryUtil.validateRoot(request.getRoot(), userId);

            category.setLabel(replaceIfNotNull(category.getLabel(), request.getLabel()));
            category.setDescription(replaceIfNotNull(category.getDescription(), request.getDescription()));

            validateRoot(request.getRoot(), category.getRoot(), category.getCategoryId());

            category.setRoot(replaceIfNotNull(category.getRoot(), request.getRoot()));

            categoryDao.save(category);
        });
    }

    private void validateRoot(String newRoot, String oldRoot, String categoryId) {
        if (isNull(newRoot)) {
            return;
        }

        if (newRoot.equals(oldRoot)) {
            return;
        }

        if (newRoot.equals(categoryId)) {
            throw new BadRequestException("New root must not equal categoryId");
        }

        validateNotChildren(categoryId, newRoot);
    }

    private void validateNotChildren(String categoryId, String newRoot) {
        categoryDao.getByRoot(categoryId).stream()
            .peek(category -> validateNotChildren(category.getCategoryId(), newRoot))
            .filter(category -> category.getCategoryId().equals(newRoot))
            .findAny()
            .ifPresent(c -> {
                throw new BadRequestException("New root must not be any of the category's children.");
            });
    }

    public List<Category> getByUserId(String userId) {
        return categoryDao.getByUserId(userId);
    }
}
