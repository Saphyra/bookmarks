package bookmarks.controller;

import bookmarks.auth.PropertySourceImpl;
import bookmarks.controller.request.data.CreateLinkRequest;
import bookmarks.controller.request.data.UpdateLinkRequest;
import bookmarks.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LinkController {
    private static final String CREATE_LINK_MAPPING = "link";
    private static final String DELETE_LINK_MAPPING = "link";
    private static final String UPDATE_LINK_MAPPING = "link";

    private final LinkService linkService;

    @PutMapping(CREATE_LINK_MAPPING)
    public void createLink(
        @RequestBody @Valid CreateLinkRequest request,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to create a new link.", userId);
        linkService.create(request, userId);
    }

    @DeleteMapping(DELETE_LINK_MAPPING)
    public void deleteLink(
        @RequestBody List<String> linkIds,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to delete link {}.", userId, linkIds);
        linkService.delete(linkIds, userId);
    }

    @PostMapping(UPDATE_LINK_MAPPING)
    public void updateLink(
        @RequestBody @Valid List<UpdateLinkRequest> requests,
        @CookieValue(PropertySourceImpl.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to update links.", userId);
        linkService.update(requests, userId);
    }
}
