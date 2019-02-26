package org.github.bookmarks.links.common.service;

import java.util.List;

import org.github.bookmarks.links.category.CategoryFacede;
import org.github.bookmarks.links.common.DataFacade;
import org.github.bookmarks.links.common.controller.request.FilteredRequest;
import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.github.bookmarks.links.common.controller.response.DataTreeResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataFacadeImpl implements DataFacade {
    private final CategoryFacede categoryFacede;
    private final DataQueryService dataQueryService;

    @Override
    public List<DataResponse> getCategoriesOfParent(String userId, String parent) {
        return categoryFacede.getCategoriesOfParent(userId, parent);
    }

    @Override
    public List<DataTreeResponse> getCategoryTree(String userId) {
        return categoryFacede.getCategoryTree(userId);
    }

    @Override
    public List<DataResponse> getContentOfParent(String userId, String parent) {
        return dataQueryService.getContentOfParent(userId, parent);
    }

    @Override
    public List<DataResponse> getDataFiltered(FilteredRequest request, String userId) {
        return dataQueryService.getDataFiltered(request, userId);
    }
}
