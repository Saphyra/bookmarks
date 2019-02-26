package org.github.bookmarks.links.category;

import java.util.List;

import org.github.bookmarks.links.category.domain.Category;
import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.github.bookmarks.links.common.controller.response.DataTreeResponse;

public interface CategoryFacede {
    List<Category> getByUserId(String userId);

    List<DataResponse> getCategoriesOfParent(String userId, String parent);

    List<DataTreeResponse> getCategoryTree(String userId);
}
