package bookmarks.controller;

import java.util.List;
import java.util.Optional;

import bookmarks.controller.request.data.FilteredRequest;
import bookmarks.controller.response.DataTreeResponse;
import org.springframework.web.bind.annotation.*;

import bookmarks.controller.response.DataResponse;
import bookmarks.filter.FilterHelper;
import bookmarks.service.DataQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class QueryController {
    private static final String GET_CATEGORIES_MAPPING = "data/categories/{root}";
    private static final String GET_ROOT_CATEGORIES_MAPPING = "data/categories";
    private static final String GET_DATA_OF_ROOT_MAPPING = "data/root/{categoryId}";
    private static final String GET_DATA_MAPPING = "data/{root}";
    private static final String GET_DATA_FILTERED = "data";
    private static final String GET_ROOT_MAPPING = "data";
    private static final String GET_CATEGORY_TREE_MAPPING = "categories";

    private final DataQueryService dataQueryService;

    @GetMapping({GET_CATEGORIES_MAPPING, GET_ROOT_CATEGORIES_MAPPING})
    public List<DataResponse> getCategories(
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId,
        @PathVariable("root") Optional<String> parentId
    ) {
        String parent = parentId.orElse("");
        log.info("{} wants to get his data for parent {}", userId, parent);
        return dataQueryService.getCategories(userId, parent);
    }

    @GetMapping({GET_ROOT_MAPPING, GET_DATA_MAPPING})
    public List<DataResponse> getData(
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId,
        @PathVariable("root") Optional<String> parentId
    ) {
        String parent = parentId.orElse("");
        log.info("{} wants to get his data for parent {}", userId, parent);
        return dataQueryService.getDataOfCategory(userId, parent);
    }

    @PostMapping(GET_DATA_FILTERED)
    public List<DataResponse> getDataFiltered(
        @RequestBody @Valid FilteredRequest request,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to query filtered data.");
        return dataQueryService.getDataFiltered(request, userId);
    }

    @GetMapping(GET_DATA_OF_ROOT_MAPPING)
    public List<DataResponse> getDataOfRoot(
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId,
        @PathVariable("categoryId") String categoryId
    ){
        log.info("{} wants to know the data of root of {}", userId, categoryId);
        return dataQueryService.getDataOfRoot(userId, categoryId);
    }

    @GetMapping(GET_CATEGORY_TREE_MAPPING)
    public List<DataTreeResponse> getCategoryTree(@CookieValue(FilterHelper.COOKIE_USER_ID) String userId){
        log.info("{} wants to know his category tree.", userId);
        return dataQueryService.getCategoryTree(userId);
    }
}
