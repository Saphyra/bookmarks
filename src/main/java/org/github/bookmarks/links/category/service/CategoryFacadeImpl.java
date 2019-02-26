package org.github.bookmarks.links.category.service;

import java.util.List;

import org.github.bookmarks.links.category.CategoryFacede;
import org.github.bookmarks.links.category.controller.request.CreateCategoryRequest;
import org.github.bookmarks.links.category.controller.request.UpdateCategoryRequest;
import org.github.bookmarks.links.category.domain.Category;
import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.github.bookmarks.links.common.controller.response.DataTreeResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryFacadeImpl implements CategoryFacede {
    private final CategoryQueryService categoryQueryService;
    private final CreateCategoryService createCategoryService;
    private final DeleteCategoryService deleteCategoryService;
    private  final UpdateCategoryService updateCategoryService;

    @Override
    public void create(CreateCategoryRequest request, String userId) {
        createCategoryService.create(request, userId);
    }

    @Override
    public void delete(List<String> categoryIds, String userId) {
        deleteCategoryService.delete(categoryIds, userId);
    }

    @Override
    public DataResponse findCategory(String userId, String categoryId) {
        return categoryQueryService.findCategory(userId, categoryId);
    }

    @Override
    public List<Category> getByUserId(String userId) {
        return categoryQueryService.getByUserId(userId);
    }

    @Override
    public List<DataResponse> getCategoriesOfParent(String userId, String parent) {
        return categoryQueryService.getCategoriesOfParent(userId, parent);
    }

    @Override
    public List<DataTreeResponse> getCategoryTree(String userId) {
        return categoryQueryService.getCategoryTree(userId);
    }

    @Override
    public void update(List<UpdateCategoryRequest> requests, String userId) {
        updateCategoryService.update(requests, userId);
    }
}
