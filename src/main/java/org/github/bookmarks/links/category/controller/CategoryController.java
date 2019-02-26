package org.github.bookmarks.links.category.controller;

import java.util.List;

import javax.validation.Valid;

import org.github.bookmarks.auth.PropertySourceImpl;
import org.github.bookmarks.links.category.CategoryFacede;
import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.github.bookmarks.links.category.controller.request.CreateCategoryRequest;
import org.github.bookmarks.links.category.controller.request.UpdateCategoryRequest;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    private final CategoryFacede categoryFacede;

    @PutMapping(CREATE_CATEGORY_MAPPING)
    public void createCategory(
        @RequestBody @Valid CreateCategoryRequest request,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to create a new category.", userId);
        categoryFacede.create(request, userId);
    }

    @DeleteMapping(DELETE_CATEGORY_MAPPING)
    public void deleteCategory(
        @RequestBody List<String> categoryIds,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to delete categories {}.", userId, categoryIds);
        categoryFacede.delete(categoryIds, userId);
    }

    @GetMapping(GET_CATEGORY_MAPPING)
    public DataResponse getCategory(
        @PathVariable("categoryId") String categoryId,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to query category {}", userId, categoryId);
        return categoryFacede.findCategory(userId, categoryId);
    }

    @PostMapping(UPDATE_CATEGORY_MAPPING)
    public void updateCategory(
        @RequestBody @Valid List<UpdateCategoryRequest> requests,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to update categories.", userId);
        categoryFacede.update(requests, userId);
    }
}
