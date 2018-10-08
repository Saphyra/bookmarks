package bookmarks.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import bookmarks.controller.response.DataResponse;
import bookmarks.filter.FilterHelper;
import bookmarks.service.DataQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class QueryController {
    private static final String GET_DATA_MAPPING = "data/{root}";
    private static final String GET_ROOT_MAPPING = "data";

    private final DataQueryService dataQueryService;

    @GetMapping({GET_ROOT_MAPPING, GET_DATA_MAPPING})
    public List<DataResponse> getData(
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId,
        @PathVariable("root") Optional<String> parentId
    ) {
        String parent = parentId.orElse("");
        log.info("{} wants to get his data for parent {}", userId, parent);
        return dataQueryService.getDataOfUser(userId, parent);
    }
}
