package org.github.bookmarks.links.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.github.bookmarks.links.Categorizable;
import org.github.bookmarks.links.category.CategoryFacede;
import org.github.bookmarks.links.common.controller.request.FilteredRequest;
import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.github.bookmarks.links.common.util.CategorizableAccessValidator;
import org.github.bookmarks.links.common.util.CategorizableToDataResponseConverter;
import org.github.bookmarks.links.link.LinkFacade;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataQueryService {
    //TODO extract to enum
    private static final String ALL = "all";
    private static final String LINK = "link";
    private static final String CATEGORY = "category";

    private final CategoryFacede categoryFacede;
    private final CategorizableAccessValidator categorizableAccessValidator;
    private final CategorizableToDataResponseConverter converter;
    private final LinkFacade linkFacade;

    List<DataResponse> getContentOfParent(String userId, String parent) {
        List<DataResponse> categories = categoryFacede.getCategoriesOfParent(userId, parent);
        List<DataResponse> links = linkFacade.getLinksOfParent(userId, parent);

        List<DataResponse> result = Stream.concat(categories.stream(), links.stream()).collect(Collectors.toList());

        categorizableAccessValidator.validateAccessOfDataResponse(result, userId);

        return result;
    }

    List<DataResponse> getDataFiltered(FilteredRequest request, String userId) {
        List<Categorizable> result = new ArrayList<>();

        if (ALL.equals(request.getType()) || CATEGORY.equals(request.getType())) {
            result.addAll(categoryFacede.getByUserId(userId));
        }
        if (ALL.equals(request.getType()) || LINK.equals(request.getType())) {
            result.addAll(linkFacade.getByUserId(userId));
        }

        List<Categorizable> filtered = result.stream()
            .filter(item -> "".equals(request.getLabel()) || item.getLabel().toLowerCase().contains(request.getLabel().toLowerCase()))
            .filter(item -> "".equals(request.getSecondary()) || item.getSecondary().toLowerCase().contains(request.getSecondary().toLowerCase()))
            .collect(Collectors.toList());

        return converter.convertEntity(filtered);
    }
}
