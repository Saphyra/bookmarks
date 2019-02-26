package org.github.bookmarks.links.common.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.github.bookmarks.auth.PropertySourceImpl;
import org.github.bookmarks.links.common.DataFacade;
import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.github.bookmarks.links.common.controller.response.DataTreeResponse;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.github.bookmarks.links.common.controller.request.FilteredRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "unused"})
@RestController
@Slf4j
@RequiredArgsConstructor
public class QueryController {
    private static final String GET_CATEGORIES_MAPPING = "data/categories/{root}";
    private static final String GET_ROOT_CATEGORIES_MAPPING = "data/categories";
    private static final String GET_DATA_MAPPING = "data/{root}";
    private static final String GET_DATA_FILTERED = "data";
    private static final String GET_ROOT_MAPPING = "data";
    private static final String GET_CATEGORY_TREE_MAPPING = "categories";

    private final DataFacade dataFacade;

    @GetMapping({GET_CATEGORIES_MAPPING, GET_ROOT_CATEGORIES_MAPPING})
    public List<DataResponse> getCategories(
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId,
        @PathVariable("root") Optional<String> parentId
    ) {
        String parent = parentId.orElse("");
        log.info("{} wants to get his data for parent {}", userId, parent);
        return dataFacade.getCategoriesOfParent(userId, parent);
    }

    @GetMapping({GET_ROOT_MAPPING, GET_DATA_MAPPING})
    public List<DataResponse> getData(
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId,
        @PathVariable("root") Optional<String> parentId
    ) {
        String parent = parentId.orElse("");
        log.info("{} wants to get his data for parent {}", userId, parent);
        return dataFacade.getContentOfParent(userId, parent);
    }

    @PostMapping(GET_DATA_FILTERED)
    public List<DataResponse> getDataFiltered(
        @RequestBody @Valid FilteredRequest request,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to query filtered data.", userId);
        return dataFacade.getDataFiltered(request, userId);
    }

    @GetMapping(GET_CATEGORY_TREE_MAPPING)
    public List<DataTreeResponse> getCategoryTree(@CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId){
        log.info("{} wants to know his category tree.", userId);
        return dataFacade.getCategoryTree(userId);
    }
}
