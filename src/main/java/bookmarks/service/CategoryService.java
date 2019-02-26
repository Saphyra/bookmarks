package bookmarks.service;

import static java.util.Objects.isNull;
import static org.github.bookmarks.common.util.Util.replaceIfNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.github.bookmarks.common.util.CategoryUtil;
import org.github.bookmarks.user.UserFacade;
import org.springframework.stereotype.Service;

import bookmarks.controller.request.data.CreateCategoryRequest;
import bookmarks.controller.request.data.UpdateCategoryRequest;
import bookmarks.controller.response.DataResponse;
import bookmarks.controller.response.DataTreeResponse;
import bookmarks.dataaccess.CategoryDao;
import bookmarks.dataaccess.LinkDao;
import bookmarks.domain.category.Category;
import bookmarks.domain.link.Link;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryUtil categoryUtil;
    private final LinkDao linkDao;
    private final IdGenerator idGenerator;
    private final UserFacade userFacade;

    public void create(CreateCategoryRequest request, String userId) {
        userFacade.checkUserWithIdExists(userId);

        Category category = Category.builder()
            .categoryId(idGenerator.generateRandomId())
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
