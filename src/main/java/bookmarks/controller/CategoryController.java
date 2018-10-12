package bookmarks.controller;

import java.util.List;

import javax.validation.Valid;

import bookmarks.controller.response.DataResponse;
import bookmarks.domain.category.Category;
import org.springframework.web.bind.annotation.*;

import bookmarks.controller.request.CreateCategoryRequest;
import bookmarks.controller.request.UpdateCategoryRequest;
import bookmarks.filter.FilterHelper;
import bookmarks.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to create a new category.", userId);
        categoryService.create(request, userId);
    }

    @DeleteMapping(DELETE_CATEGORY_MAPPING)
    public void deleteCategory(
        @RequestBody List<String> categoryIds,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to delete categories {}.", userId, categoryIds);
        categoryService.delete(categoryIds, userId);
    }

    @GetMapping(GET_CATEGORY_MAPPING)
    public DataResponse getCategory(
        @PathVariable("categoryId") String categoryId,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to query category {}", userId, categoryId);
        return categoryService.getCategory(userId, categoryId);
    }

    @PostMapping(UPDATE_CATEGORY_MAPPING)
    public void updateCategory(
        @RequestBody @Valid List<UpdateCategoryRequest> requests,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to update categories.", userId);
        categoryService.update(requests, userId);
    }
}
