package bookmarks.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bookmarks.controller.request.CategoryRequest;
import bookmarks.filter.FilterHelper;
import bookmarks.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private static final String CREATE_CATEGORY_MAPPING = "category";
    private static final String DELETE_CATEGORY_MAPPING = "category/{categoryId}";
    private static final String UPDATE_CATEGORY_MAPPING = "category/{categoryId}";

    private final CategoryService categoryService;

    @PutMapping(CREATE_CATEGORY_MAPPING)
    public void createCategory(
        @RequestBody @Valid CategoryRequest request,
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
        @PathVariable("categoryId") String categoryId,
        @RequestBody @Valid CategoryRequest request,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to update category {}.", userId, categoryId);
        categoryService.update(request, categoryId, userId);
    }
}
