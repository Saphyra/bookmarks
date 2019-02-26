package bookmarks.controller;

import org.github.bookmarks.auth.PropertySourceImpl;
import bookmarks.controller.request.data.CreateCategoryRequest;
import bookmarks.controller.request.data.UpdateCategoryRequest;
import bookmarks.controller.response.DataResponse;
import bookmarks.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private static final String CREATE_CATEGORY_MAPPING = "category";
    private static final String DELETE_CATEGORY_MAPPING = "category";
    private static final String GET_CATEGORY_MAPPING = "category/{categoryId}";
    private static final String UPDATE_CATEGORY_MAPPING = "category";

    private final CategoryService categoryService;

    @PutMapping(CREATE_CATEGORY_MAPPING)
    public void createCategory(
        @RequestBody @Valid CreateCategoryRequest request,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to create a new category.", userId);
        categoryService.create(request, userId);
    }

    @DeleteMapping(DELETE_CATEGORY_MAPPING)
    public void deleteCategory(
        @RequestBody List<String> categoryIds,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to delete categories {}.", userId, categoryIds);
        categoryService.delete(categoryIds, userId);
    }

    @GetMapping(GET_CATEGORY_MAPPING)
    public DataResponse getCategory(
        @PathVariable("categoryId") String categoryId,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to query category {}", userId, categoryId);
        return categoryService.getCategory(userId, categoryId);
    }

    @PostMapping(UPDATE_CATEGORY_MAPPING)
    public void updateCategory(
        @RequestBody @Valid List<UpdateCategoryRequest> requests,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to update categories.", userId);
        categoryService.update(requests, userId);
    }
}
