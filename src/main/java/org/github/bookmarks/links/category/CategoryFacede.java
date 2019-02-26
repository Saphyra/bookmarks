package org.github.bookmarks.links.category;

import java.util.List;

import org.github.bookmarks.links.category.controller.request.CreateCategoryRequest;
import org.github.bookmarks.links.category.controller.request.UpdateCategoryRequest;
import org.github.bookmarks.links.category.domain.Category;
import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.github.bookmarks.links.common.controller.response.DataTreeResponse;

public interface CategoryFacede {
    void create(CreateCategoryRequest request, String userId);

    void delete(List<String> categoryIds, String userId);

    DataResponse findCategory(String userId, String categoryId);

    List<Category> getByUserId(String userId);

    List<DataResponse> getCategoriesOfParent(String userId, String parent);

    List<DataTreeResponse> getCategoryTree(String userId);

    void update(List<UpdateCategoryRequest> requests, String userId);
}
