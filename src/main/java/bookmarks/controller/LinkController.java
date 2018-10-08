package bookmarks.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bookmarks.controller.request.LinkRequest;
import bookmarks.filter.FilterHelper;
import bookmarks.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LinkController {
    private static final String CREATE_LINK_MAPPING = "link";
    private static final String DELETE_LINK_MAPPING = "link/{linkId}";

    private final LinkService linkService;

    @PutMapping(CREATE_LINK_MAPPING)
    public void createLink(
        @RequestBody @Valid LinkRequest request,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to create a new link.", userId);
        linkService.create(request, userId);
    }

    @DeleteMapping(DELETE_LINK_MAPPING)
    public void deleteLink(
        @PathVariable("linkId") String linkId,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to delete link {}.", userId, linkId);
        linkService.delete(linkId, userId);
    }
}
