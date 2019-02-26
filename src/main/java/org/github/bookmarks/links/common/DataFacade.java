package org.github.bookmarks.links.common;

import java.util.List;

import org.github.bookmarks.links.common.controller.request.FilteredRequest;
import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.github.bookmarks.links.common.controller.response.DataTreeResponse;

public interface DataFacade {
    List<DataResponse> getCategoriesOfParent(String userId, String parent);

    List<DataTreeResponse> getCategoryTree(String userId);

    List<DataResponse> getContentOfParent(String userId, String parent);

    List<DataResponse> getDataFiltered(FilteredRequest request, String userId);
}
