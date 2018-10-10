package bookmarks.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(UPDATE_CATEGORY_MAPPING)
    public void updateCategory(
        @RequestBody @Valid List<UpdateCategoryRequest> requests,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to update categories.", userId);
        categoryService.update(requests, userId);
    }
}
